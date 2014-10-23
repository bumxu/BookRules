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

package com.mstiles92.plugins.bookrules.menu.menus;

import com.mstiles92.plugins.bookrules.BookRules;
import com.mstiles92.plugins.bookrules.data.StoredBook;
import com.mstiles92.plugins.bookrules.data.StoredBooks;
import com.mstiles92.plugins.bookrules.menu.items.BookMenuItem;
import com.mstiles92.plugins.bookrules.util.BookUtils;
import com.mstiles92.plugins.stileslib.menu.events.MenuClickEvent;
import com.mstiles92.plugins.stileslib.menu.items.MenuItem;
import com.mstiles92.plugins.stileslib.menu.menus.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MainMenu extends Menu {
    private static final int NUM_USED_ROWS = 4;
    private int page = 1;

    public MainMenu() {
        super(BookRules.getInstance(), "BookRules Main Menu", 6);

        setItem(45, new MenuItem(new ItemStack(Material.NETHER_STAR), "Previous Page") {
            @Override
            public void onClick(MenuClickEvent event) {
                page -= 1;

                applyPage(event.getPlayer());
                event.setResult(MenuClickEvent.Result.REFRESH);
            }

            @Override
            public boolean visibleTo(Player player) {
                return page > 1;
            }
        });

        setItem(53, new MenuItem(new ItemStack(Material.NETHER_STAR), "Next Page") {
            @Override
            public void onClick(MenuClickEvent event) {
                page += 1;

                applyPage(event.getPlayer());
                event.setResult(MenuClickEvent.Result.REFRESH);
            }

            @Override
            public boolean visibleTo(Player player) {
                return BookUtils.filterListByPermission(StoredBooks.getStoredBooks(), player).size() > page * 9 * NUM_USED_ROWS;
            }
        });

        setItem(49, new MenuItem(new ItemStack(Material.NETHER_STAR), "Admin Menu") {
            @Override
            public void onClick(MenuClickEvent event) {
                event.getPlayer().sendMessage("Admin Menu");
                event.setResult(MenuClickEvent.Result.CLOSE);
            }

            @Override
            public boolean visibleTo(Player player) {
                return player.isOp();
            }
        });
    }

    private void applyPage(Player player) {
        List<StoredBook> bookList = BookUtils.filterListByPermission(StoredBooks.getStoredBooks(), player);

        for (int i = 0; i < 9 * NUM_USED_ROWS; i++) {
            int index = i + ((page - 1) * 9 * NUM_USED_ROWS);

            setItem(i, index < bookList.size() ? new BookMenuItem(bookList.get(index)) : null);
        }
    }

    @Override
    public void open(Player player) {
        applyPage(player);
        super.open(player);
    }
}
