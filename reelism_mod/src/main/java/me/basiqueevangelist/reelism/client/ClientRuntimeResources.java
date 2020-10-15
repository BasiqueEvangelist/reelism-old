package me.basiqueevangelist.reelism.client;

import com.swordglowsblue.artifice.api.Artifice;
import me.basiqueevangelist.reelism.Reelism;
import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class ClientRuntimeResources {
    public static void register() {
        Artifice.registerAssets(new Identifier(Reelism.NAMESPACE, "runtime_resources"), b -> {
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
            if (Reelism.CONFIG.tntIsPowderKeg) {
                b.addBlockModel(new Identifier("tnt"), m -> {
                    m.parent(new Identifier("block/cube_bottom_top"));
                    m.texture("top", new Identifier("reelism:block/powder_keg_top"));
                    m.texture("bottom", new Identifier("reelism:block/powder_keg_bottom"));
                    m.texture("side", new Identifier("reelism:block/powder_keg_side"));
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
        });
    }

    private static final int[] DIRECTION_TO_Y = new int[] { 90, 180, 270, 0 };
}
