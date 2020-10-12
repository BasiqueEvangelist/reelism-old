package ml.porez.reelism;

import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder;
import net.minecraft.util.Identifier;

public class RuntimeResources {
    public static void register() {
        ArtificeResourcePack pack = ArtificeResourcePack.ofData(b -> {
            if (Reelism.CONFIG.torchesBurnOut) {
                b.addLootTable(new Identifier("blocks/torch"), l -> {
                    l.type(new Identifier("block"));
                    l.pool(p -> {
                        p.rolls(1);
                        p.condition(new Identifier("survives_explosion"), o -> {});
                        p.entry(e -> {
                            e.type(new Identifier("alternatives"));
                            e.child(e1 -> {
                                e1.type(new Identifier("empty"));
                                e1.condition(new Identifier("block_state_property"), o -> {
                                    o.add("block", "minecraft:torch");
                                    JsonObjectBuilder prop = new JsonObjectBuilder();
                                    prop.add("age", "25");
                                    o.add("properties", prop.build());
                                });
                            });
                            e.child(e1 -> {
                                e1.type(new Identifier("empty"));
                                e1.condition(new Identifier("block_state_property"), o -> {
                                    o.add("block", "minecraft:wall_torch");
                                    JsonObjectBuilder prop = new JsonObjectBuilder();
                                    prop.add("age", "25");
                                    o.add("properties", prop.build());
                                });
                            });
                            e.child(e1 -> {
                                e1.type(new Identifier("item"));
                                e1.name(new Identifier("torch"));
                            });
                        });
                    });
                });
            }
        });
        Artifice.registerData(new Identifier(Reelism.NAMESPACE, "runtime_data"), pack);
    }
}
