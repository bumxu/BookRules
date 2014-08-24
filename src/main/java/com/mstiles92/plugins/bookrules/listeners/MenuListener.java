/*
 * This document is a part of the source code and related artifacts for
 * BookRules, an open source Bukkit plugin for automatically distributing
 * written books to new players.
 *
 * http://dev.bukkit.org/bukkit-plugins/bookrules/
 * http://github.com/mstiles92/BookRules
 *
 * Copyright (c) 2014 Matthew Stiles (mstiles92)
 *
 * Licensed under the Common Development and Distribution License Version 1.0
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the CDDL-1.0 License at
 * http://opensource.org/licenses/CDDL-1.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the license.
 */

package com.mstiles92.plugins.bookrules.listeners;

import com.mstiles92.plugins.bookrules.data.PlayerData;
import com.mstiles92.plugins.bookrules.menu.MenuProvider;
import com.mstiles92.plugins.bookrules.menu.MenuType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MenuListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        PlayerData playerData = PlayerData.get(player);
        if (!playerData.getCurrentMenuType().equals(MenuType.NONE)) {
            event.setCancelled(true);
            MenuProvider.getNewMenuHandler(playerData.getCurrentMenuType()).handleClick(player, event.getCurrentItem());
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        PlayerData playerData = PlayerData.get(player);
        if (!playerData.getCurrentMenuType().equals(MenuType.NONE)) {
            MenuProvider.getNewMenuHandler(playerData.getCurrentMenuType()).handleClose(player);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        PlayerData playerData = PlayerData.get(event.getPlayer());
        if (!playerData.getCurrentMenuType().equals(MenuType.NONE)) {
            MenuProvider.getNewMenuHandler(playerData.getCurrentMenuType()).handleClose(event.getPlayer());
        }
    }
}
