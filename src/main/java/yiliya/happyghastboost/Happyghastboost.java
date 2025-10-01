package yiliya.happyghastboost;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import yiliya.happyghastboost.item.SnowballOnAStickItem;

public class Happyghastboost implements ModInitializer {

    public static final String MOD_ID = "happyghastboost";
    public static Item SNOWBALL_ON_A_STICK;

    @Override
    public void onInitialize() {
        SNOWBALL_ON_A_STICK = Items.register(
                RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, "snowball_on_a_stick")),
                SnowballOnAStickItem::new,
                new Item.Settings().maxDamage(100)
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries ->
            entries.add(SNOWBALL_ON_A_STICK)
        );
    }
}