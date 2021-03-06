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

import com.mstiles92.plugins.bookrules.BookRules;
import com.mstiles92.plugins.bookrules.config.Config;
import org.bukkit.ChatColor;

public class Log {
    public static void verbose(String message) {
        if (Config.verboseOutputEnabled()) {
            BookRules.getInstance().getLogger().info(message);
        }
    }

    public static void info(String message) {
        BookRules.getInstance().getLogger().info(message);
    }

    public static void warning(String message) {
        BookRules.getInstance().getLogger().warning(ChatColor.RED + message);
    }
}
