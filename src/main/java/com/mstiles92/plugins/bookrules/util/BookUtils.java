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

package com.mstiles92.plugins.bookrules.util;

import com.mstiles92.plugins.bookrules.data.StoredBook;
import com.mstiles92.plugins.bookrules.data.StoredBooks;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.UUID;

public class BookUtils {
    private static final int MAX_TITLE_LENGTH = 16;
    private static final int MAX_AUTHOR_LENGTH = 16;

    /**
     * Verify that the provided ItemStack is a valid BookRules book.
     *
     * @param item the ItemStack to check
     * @return true if the ItemStack is a BookRules book, false if it is not
     */
    public static boolean isBookRulesBook(ItemStack item) {
        if (item != null && item.getType().equals(Material.WRITTEN_BOOK)) {
            try {
                AttributeWrapper wrapper = AttributeWrapper.newWrapper(item);
                UUID uuid = UUID.fromString(wrapper.getData());
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }

        return false;
    }

    /**
     * Change the title of the given book.
     *
     * @param book  an ItemStack of the book to be changed
     * @param title the title to be applied to the book, truncated if necessary
     * @return the modified book
     */
    public static ItemStack setTitle(ItemStack book, String title) {
        BookMeta meta = (BookMeta) book.getItemMeta();
        meta.setTitle((title.length() < MAX_TITLE_LENGTH) ? title : title.substring(0, MAX_TITLE_LENGTH));
        book.setItemMeta(meta);
        return book;
    }

    /**
     * Change the author of the given book.
     *
     * @param book   an ItemStack of the book to be changed
     * @param author the author to be applied to the book, truncated if necessary
     * @return the modified book
     */
    public static ItemStack setAuthor(ItemStack book, String author) {
        BookMeta meta = (BookMeta) book.getItemMeta();
        meta.setAuthor((author.length() < MAX_AUTHOR_LENGTH) ? author : author.substring(0, MAX_AUTHOR_LENGTH));
        book.setItemMeta(meta);
        return book;
    }

    /**
     * Unsign a written book, changing it back into a book and quill with all
     * of the pages unaltered.
     *
     * @param book an ItemStack of the Written Book to unsign
     * @return an ItemStack of the unsigned Book and Quill
     */
    public static ItemStack unsignBook(ItemStack book) {
        BookMeta oldMeta = (BookMeta) book.getItemMeta();
        ItemStack unsignedBook = new ItemStack(Material.BOOK_AND_QUILL, 1);
        BookMeta newMeta = (BookMeta) unsignedBook.getItemMeta();
        newMeta.setPages(oldMeta.getPages());
        unsignedBook.setItemMeta(newMeta);
        return unsignedBook;
    }

    /**
     * Give a player all books stored by the plugin that they have permission to view.
     *
     * @param player the player who should be given all books
     * @return the number of books given to the player
     */
    public static int givePlayerAllBooks(Player player) {
        int count = 0;

        for (StoredBook book : StoredBooks.getStoredBooks()) {
            if (book.checkPermission(player)) {
                count += 1;

                book.giveToPlayer(player);
            }
        }

        return count;
    }

    /**
     * Give a player all books they have permission to view and have not yet received.
     *
     * @param player the player who should be given books
     * @return the number of books given to the player
     */
    public static int givePlayerAllUngivenBooks(Player player) {
        //TODO: implement
        return -1;
    }
}
