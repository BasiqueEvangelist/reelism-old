package ml.porez.reelism;

import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class RuntimeResources {
    public static void register() {
        ArtificeResourcePack data = ArtificeResourcePack.ofData(b -> {
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
        Artifice.registerData(new Identifier(Reelism.NAMESPACE, "runtime_data"), data);
        ArtificeResourcePack res = ArtificeResourcePack.ofAssets(b -> {
            if (Reelism.CONFIG.torchesBurnOut) {
                b.addBlockState(new Identifier("torch"), s -> {
                    s.variant("age=25", v -> {
                        v.model(new Identifier("reelism:block/burnt_torch"));
                    });
                    for (int i = 0; i < 25; i++)
                        s.variant("age=" + i, v -> {
                            v.model(new Identifier("minecraft:block/torch"));
                        });
                });

                b.addBlockState(new Identifier("wall_torch"), s -> {
                    for (int i = 0; i < 4; i++) {
                        Direction dir = Direction.fromHorizontal(i);
                        int yRot = DIRECTION_TO_Y[i];
                        for (int j = 0; j < 25; j++) {
                            s.variant("facing=" + dir.asString() + ",age=" + j, v -> {
                                v.model(new Identifier("minecraft:block/wall_torch"));
                                v.rotationY(yRot);
                            });
                        }
                        s.variant("facing=" + dir.asString() + ",age=25", v -> {
                            v.model(new Identifier("reelism:block/burnt_wall_torch"));
                            v.rotationY(yRot);
                        });
                    }
                });
            }
        });
        Artifice.registerAssets(new Identifier(Reelism.NAMESPACE, "runtime_resources"), res);
    }

    private static final int[] DIRECTION_TO_Y = new int[] {90, 180, 270, 0};
}
