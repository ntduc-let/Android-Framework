/*
 * Copyright (C) 2020 Veli Tasalı
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.ntduc.androidframework.framework.util.actionperformer;

import com.ntduc.androidframework.framework.object.Selectable;

import java.util.List;

/**
 * The idea here is that, by separating the selected items' holder from the {@link IEngineConnection} class, we can
 * store and restore list in the case of the items being deleted.
 *
 * @param <T> The derivative of the {@link Selectable} class
 */
public interface SelectableHost<T extends Selectable>
{
    /**
     * @return the items marked as selected
     */
    List<T> getSelectableList();
}
