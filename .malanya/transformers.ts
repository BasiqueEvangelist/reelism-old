import { Item } from "./index"

export function purgeFromLootTables(id: string): (Item) => any {
    function checkCondition(conds: any[]): boolean {
        for (let condition of conds) {
            if (condition.condition == "minecraft:match_tool") {
                if (condition.predicate.item == id) {
                    return true;
                }
            }
        }
        return false;
    }
    function checkEntry(entry: any): boolean {
        if (["minecraft:group", "minecraft:alternatives", "minecraft:sequence"].includes(entry.type))
            for (let j = 0; j < entry.children.length; j++) {
                let subEntry = entry.children[j];

                if (checkEntry(subEntry)) {
                    j--;
                    entry.children.splice(entry.children.indexOf(subEntry), 1);
                }
            }
        if (entry.conditions)
            if (checkCondition(entry.conditions))
                return true;
        if (entry.type == "minecraft:item" && entry.name == id)
            return true;

        return false;
    }

    return function (i: Item) {
        if (i.type == "loot_tables") {
            if (i.data.pools)
                for (let j = 0; j < i.data.pools.length; j++) {
                    let pool = i.data.pools[j];

                    if (pool.conditions && checkCondition(pool.conditions)) {
                        j--;
                        i.data.pools.splice(i.data.pools.indexOf(pool), 1);
                        continue;
                    }

                    for (let k = 0; k < pool.entries.length; k++) {
                        let entry = pool.entries[k];

                        if (checkEntry(entry)) {
                            k--;
                            pool.entries.splice(pool.entries.indexOf(entry), 1);
                        }
                    }
                }

        }
        return i.data;
    };
}