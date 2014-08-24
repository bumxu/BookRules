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

package com.mstiles92.plugins.bookrules.menu.handlers;

import com.mstiles92.plugins.bookrules.data.PlayerData;
import com.mstiles92.plugins.bookrules.data.StoredBook;
import com.mstiles92.plugins.bookrules.data.StoredBooks;
import com.mstiles92.plugins.bookrules.menu.IMenuHandler;
import com.mstiles92.plugins.bookrules.menu.MenuType;
import com.mstiles92.plugins.bookrules.util.AttributeWrapper;
import com.mstiles92.plugins.bookrules.util.BookUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MainMenuHandler implements IMenuHandler {
    private static ItemStack adminItem;

    static {
        adminItem = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = adminItem.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE + "Admin Menu");
        adminItem.setItemMeta(meta);
    }

    @Override
    public void openMenu(Player player) {
        List<StoredBook> books = BookUtils.filterListByPermission(StoredBooks.getStoredBooks(), player);
        int numRows = (books.size() == 0) ? 0 : (books.size() / 9) + 1;
        if (player.hasPermission("bookrules.admin")) {
            numRows += 1;
        }
        //TODO: check for too many rows and apply pagination

        Inventory inv = Bukkit.createInventory(player, numRows * 9, "BookRules Main Menu");
        for (StoredBook i : books) {
            inv.addItem(i.getItemStack());
        }

        //TODO: add item for getting all books at once

        if (player.hasPermission("bookrules.admin")) {
            inv.setItem((numRows * 9) - 5, adminItem);
        }

        player.openInventory(inv);
        PlayerData.get(player).setCurrentMenuType(getMenuType());
    }

    @Override
    public void handleClick(Player player, ItemStack clickedItem) {
        if (clickedItem == null || clickedItem.getType().equals(Material.AIR)) {
            return;
        }

        if (clickedItem.getType().equals(Material.WRITTEN_BOOK)) {
            AttributeWrapper wrapper = AttributeWrapper.newWrapper(clickedItem);
            player.sendMessage("Book UUID: " + wrapper.getData());
            //TODO: actually give player book
        } else if (clickedItem.getType().equals(Material.NETHER_STAR)) {
            player.sendMessage("Admin Menu");
            //TODO: actually open up the Admin Menu
        }
    }

    @Override
    public void handleClose(Player player) {
        PlayerData.get(player).setCurrentMenuType(MenuType.NONE);
    }

    @Override
    public MenuType getMenuType() {
        return MenuType.MAIN;
    }
}
