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

import java.util.ArrayList;
import java.util.List;

public class PerformerEngine implements IPerformerEngine
{
    private final List<IBaseEngineConnection> mConnectionList = new ArrayList<>();
    private final List<PerformerCallback> mPerformerCallbackList = new ArrayList<>();
    private final List<PerformerListener> mPerformerListenerList = new ArrayList<>();

    public <T extends Selectable> boolean check(IEngineConnection<T> engineConnection, T selectable, boolean isSelected,
                                                int position)
    {
        synchronized (mPerformerCallbackList) {
            for (PerformerCallback callback : mPerformerCallbackList)
                if (!callback.onSelection(this, engineConnection, selectable, isSelected, position))
                    return false;
        }

        return true;
    }

    @Override
    public <T extends Selectable> boolean check(IEngineConnection<T> engineConnection, List<T> selectableList,
                                                boolean isSelected, int[] positions)
    {
        synchronized (mPerformerCallbackList) {
            for (PerformerCallback callback : mPerformerCallbackList)
                if (!callback.onSelection(this, engineConnection, selectableList, isSelected, positions))
                    return false;
        }

        return true;
    }

    @Override
    public List<? extends Selectable> getSelectionList()
    {
        List<Selectable> selectableList = new ArrayList<>();

        synchronized (mConnectionList) {
            for (IBaseEngineConnection baseEngineConnection : mConnectionList)
                selectableList.addAll(baseEngineConnection.getGenericSelectedItemList());
        }

        return selectableList;
    }

    @Override
    public List<IBaseEngineConnection> getConnectionList()
    {
        return new ArrayList<>(mConnectionList);
    }

    public boolean hasActiveSlots()
    {
        return mConnectionList.size() > 0;
    }

    @Override
    public boolean ensureSlot(PerformerEngineProvider provider, IBaseEngineConnection selectionConnection)
    {
        synchronized (mConnectionList) {
            if (mConnectionList.contains(selectionConnection) || mConnectionList.add(selectionConnection)) {
                if (selectionConnection.getEngineProvider() != provider)
                    selectionConnection.setEngineProvider(provider);

                return true;
            }
        }

        return false;
    }

    @Override
    public <T extends Selectable> void informListeners(IEngineConnection<T> engineConnection, T selectable,
                                                       boolean isSelected, int position)
    {
        synchronized (mPerformerListenerList) {
            for (PerformerListener listener : mPerformerListenerList)
                listener.onSelected(this, engineConnection, selectable, isSelected, position);
        }
    }

    @Override
    public <T extends Selectable> void informListeners(IEngineConnection<T> engineConnection, List<T> selectableList,
                                                       boolean isSelected, int[] positions)
    {
        synchronized (mPerformerListenerList) {
            for (PerformerListener listener : mPerformerListenerList)
                listener.onSelected(this, engineConnection, selectableList, isSelected, positions);
        }
    }

    @Override
    public boolean removeSlot(IBaseEngineConnection selectionConnection)
    {
        synchronized (mConnectionList) {
            return mConnectionList.remove(selectionConnection);
        }
    }

    @Override
    public void removeSlots()
    {
        synchronized (mConnectionList) {
            mConnectionList.clear();
        }
    }

    @Override
    public boolean addPerformerCallback(PerformerCallback callback)
    {
        synchronized (mPerformerCallbackList) {
            return mPerformerCallbackList.contains(callback) || mPerformerCallbackList.add(callback);
        }
    }

    @Override
    public boolean addPerformerListener(PerformerListener listener)
    {
        synchronized (mPerformerListenerList) {
            return mPerformerListenerList.contains(listener) || mPerformerListenerList.add(listener);
        }
    }

    @Override
    public boolean removePerformerCallback(PerformerCallback listener)
    {
        synchronized (mPerformerCallbackList) {
            return mPerformerCallbackList.remove(listener);
        }
    }

    @Override
    public boolean removePerformerListener(PerformerListener listener)
    {
        synchronized (mPerformerListenerList) {
            return mPerformerListenerList.remove(listener);
        }
    }
}
