package me.cjcrafter.tileentity.compatibility;

import me.cjcrafter.tileentity.TileEntityLagFix;
import me.deecaad.core.utils.Debugger;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class v1_17_R1 implements TileEntityCompatibility {

    private static Debugger debug;

    public v1_17_R1() {
        if (debug == null) {
            debug = TileEntityLagFix.getInstance().getDebug();
        }
    }

    @Override
    public List<?> getTileList(Set<String> blacklist) {
        return new TileEntityList(blacklist);
    }

    private static class TileEntityList extends ArrayList<TileEntity> {

        private final Set<String> blacklist;

        private TileEntityList(Set<String> blacklist) {
            this.blacklist = blacklist.stream().map(String::toLowerCase).collect(Collectors.toSet());
        }

        @Override
        public boolean add(TileEntity tile) {
            String name = Objects.requireNonNull(TileEntityTypes.a(tile.getTileType())).getKey();
            if (blacklist.contains(name)) {
                debug.debug("Filtering out " + name);
                return false;
            } else {
                debug.debug("Keeping " + name);
                return super.add(tile);
            }
        }
    }
}
