package camscythe;

import camscythe.item.EmberglaiveItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {

    private static final ToolMaterial EMBERGLAIVE_MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_IRON_TOOL, 2869, 1.0f, 0.0f, 14, ItemTags.IRON_TOOL_MATERIALS
    );
    private static final ToolMaterial PLAYTHING_MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_IRON_TOOL, 2764, 1.0f, 0.0f, 14, ItemTags.IRON_TOOL_MATERIALS
    );
    private static final ToolMaterial VINECOG_MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2869, 1.0f, 0.0f, 22, ItemTags.NETHERITE_TOOL_MATERIALS
    );

    public static final Item EMBERGLAIVE = register(
            "emberglaive",
            Item::new,
            (new Item.Settings().sword(EMBERGLAIVE_MATERIAL,7.0f,-2.8f))
    );

    public static final Item PLAYTHING = register(
            "plaything",
            Item::new,
            (new Item.Settings().sword(PLAYTHING_MATERIAL,8.0f,-2.0f))
    );
    public static final Item VINECOG = register(
            "vinecog",
            Item::new,
            (new Item.Settings().sword(VINECOG_MATERIAL,11.0f,-2.7f))
    );

    private static Item register(String name, java.util.function.Function<RegistryKey<Item>, Item> factory) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of("camscythe", name));
        return Registry.register(Registries.ITEM, key, factory.apply(key));
    }

    private static Item register(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return register(RegistryKey.of(RegistryKeys.ITEM,Identifier.of("camscythe",name)),factory,settings);
    }

    private static Item register(RegistryKey<Item> key, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(key));
        if (item instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return (Item)Registry.register(Registries.ITEM, key, item);
    }

    public static void initialize() {}
}
