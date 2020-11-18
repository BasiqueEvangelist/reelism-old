package me.basiqueevangelist.reelism.client;

import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import me.basiqueevangelist.reelism.Reelism;
import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class ClientRuntimeResources {
    public static void register() {
        ArtificeResourcePack arp = ArtificeResourcePack.ofAssets(b -> {
            if (Reelism.CONFIG.torchesBurnOut) {
                b.addBlockState(new Identifier("torch"), s -> {
                    s.variant("age=25", v -> {
                        v.model(Reelism.id("block/burnt_torch"));
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
                            v.model(Reelism.id("block/burnt_wall_torch"));
                            v.rotationY(yRot);
                        });
                    }
                });
            }
            if (Reelism.CONFIG.tntIsPowderKeg) {
                b.addBlockModel(new Identifier("tnt"), m -> {
                    m.parent(new Identifier("block/cube_bottom_top"));
                    m.texture("top", Reelism.id("block/powder_keg_top"));
                    m.texture("bottom", Reelism.id("block/powder_keg_bottom"));
                    m.texture("side", Reelism.id("block/powder_keg_side"));
                });
            }

            b.addLanguage(new LanguageDefinition("en_us", "US", "English", false));
            b.addTranslations(new Identifier("en_us"), l -> {
                if (Reelism.CONFIG.tntIsPowderKeg) {
                    l.entry("block.minecraft.tnt", "Powder Keg");
                    l.entry("item.minecraft.tnt_minecart", "Minecart with Powder Keg");
                }
                if (Reelism.CONFIG.enchantments.smiteIsFungicide) {
                    l.entry("enchantment.minecraft.smite", "Fungicide");
                }
            });

            b.addTranslations(new Identifier("ru_ru"), l -> {
                if (Reelism.CONFIG.tntIsPowderKeg) {
                    l.entry("block.minecraft.tnt", "Пороховая бочка");
                    l.entry("item.minecraft.tnt_minecart", "Вагонетка с пороховой бочкой");
                }
                if (Reelism.CONFIG.enchantments.smiteIsFungicide) {
                    l.entry("enchantment.minecraft.smite", "Фунгицид");
                }
            });
            if (Reelism.CONFIG.surreelism.altNetherPortals) {
                b.addBlockState(Reelism.id("nether_portal_frame"), s -> {
                    for (int dirId = 0; dirId < 4; dirId++) {
                        Direction dir = Direction.fromHorizontal(dirId);
                        for (int variant = 1; variant <= 4; variant++) {
                            int variantF = variant;
                            s.weightedVariant("facing=" + dir.asString(), v -> {
                                v.model(Reelism.id("block/nether_portal_frame_" + variantF));
                                v.rotationY((int) dir.asRotation());
                            });
                        }
                    }
                });

                for (int variant = 1; variant <= 4; variant++) {
                    int variantF = variant;
                    b.addBlockModel(Reelism.id("nether_portal_frame_" + variant), m -> {
                        m.parent(new Identifier("block/block"));

                        m.texture("particle", Reelism.id("block/nether_portal_frame_side_" + variantF));
                        m.texture("bottom", new Identifier("block/obsidian"));
                        m.texture("top", Reelism.id("block/nether_portal_frame_top"));
                        m.texture("side", Reelism.id("block/nether_portal_frame_side_" + variantF));
                        m.texture("eye", Reelism.id("block/nether_portal_frame_eye_" + variantF));

                        m.element(e -> {
                            e.from(0, 0, 0);
                            e.to(16, 13, 16);

                            e.face(Direction.DOWN, f -> {
                                f.uv(0, 0, 16, 16);
                                f.texture("bottom");
                                f.cullface(Direction.DOWN);
                            });
                            e.face(Direction.UP, f -> {
                                f.uv(0, 0, 16, 16);
                                f.texture("top");
                            });
                            e.face(Direction.NORTH, f -> {
                                f.uv(0, 3, 16, 16);
                                f.texture("side");
                                f.cullface(Direction.NORTH);
                            });
                            e.face(Direction.SOUTH, f -> {
                                f.uv(0, 3, 16, 16);
                                f.texture("side");
                                f.cullface(Direction.SOUTH);
                            });
                            e.face(Direction.WEST, f -> {
                                f.uv(0, 3, 16, 16);
                                f.texture("side");
                                f.cullface(Direction.WEST);
                            });
                            e.face(Direction.EAST, f -> {
                                f.uv(0, 3, 16, 16);
                                f.texture("side");
                                f.cullface(Direction.EAST);
                            });
                        });

                        m.element(e -> {
                            e.from(4, 13, 4);
                            e.to(12, 16, 12);

                            e.face(Direction.UP, f -> {
                                f.uv(4, 4, 12, 12);
                                f.texture("eye");
                                f.cullface(Direction.UP);
                            });
                            e.face(Direction.NORTH, f -> {
                                f.uv(4, 0, 12, 3);
                                f.texture("eye");
                            });
                            e.face(Direction.SOUTH, f -> {
                                f.uv(4, 0, 12, 3);
                                f.texture("eye");
                            });
                            e.face(Direction.WEST, f -> {
                                f.uv(4, 0, 12, 3);
                                f.texture("eye");
                            });
                            e.face(Direction.EAST, f -> {
                                f.uv(4, 0, 12, 3);
                                f.texture("eye");
                            });
                        });
                    });
                }
            }
        });
        Artifice.registerAssets(Reelism.id("runtime_resources"), arp);
    }

    private static final int[] DIRECTION_TO_Y = new int[] { 90, 180, 270, 0 };
}
