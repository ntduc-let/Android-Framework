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

package com.ntduc.androidframework.framework.util;

import java.util.List;

/**
 * created by: Veli
 * date: 20.11.2017 00:15
 * <p>
 * A way of informing threads and objects. The aim is to make sure the same object can be used more than one places
 * (threads and UI elements). It also helps you make you are closing or removing temporary objects when the task is
 * cancelled.
 */
public interface Stoppable
{
    /**
     * Add an object to be invoked when the task is cancelled.
     *
     * @param closer to be called when the {@link #interrupt()} is called
     * @return true when adding to the list is successful
     */
    boolean addCloser(Closer closer);

    /**
     * Check if the callback was previously added to the list.
     *
     * @param closer to be checked
     * @return true if it was already added
     */
    boolean hasCloser(Closer closer);

    /**
     * Objects pending to be called when the task is called.
     *
     * @return pending list of objects
     */
    List<Closer> getClosers();

    /**
     * Ensure if the task has been cancelled.
     *
     * @return true if it was
     */
    boolean isInterrupted();

    /**
     * Was the task called with {@link #interrupt(boolean)} with userAction boolean set to true?
     *
     * @return true if the was cancelled with userAction boolean was true
     */
    boolean isInterruptedByUser();

    /**
     * Cancel the task with 'userAction' is set to true.
     *
     * @see #interrupt(boolean)
     */
    boolean interrupt();

    /**
     * Cancel the task and call the {@link Closer} objects if it was not cancelled previously.
     *
     * @param userAction true if it is performed by user
     * @return true if it was not cancelled before
     */
    boolean interrupt(boolean userAction);

    /**
     * Remove a previously added @link Closer} object from the list.
     *
     * @param closer to be removed
     * @return true if it has been removed
     */
    boolean removeCloser(Closer closer);

    /**
     * @see #reset(boolean)
     */
    void reset();

    /**
     * Reset the interrupted flags and remove {@link Closer} objects if needed.
     *
     * @param resetClosers true if you want to remove the {@link Closer} objects
     */
    void reset(boolean resetClosers);

    /**
     * Remove all closers.
     */
    void removeClosers();

    /**
     * When interrupted, invoke this. This will not be called a second time.
     */
    interface Closer
    {
        /**
         * {@link Stoppable#interrupt(boolean)} will invoke this when an instance is provided using
         * {@link Stoppable#addCloser(Closer)}.
         *
         * @param userAction true the {@link Stoppable#interrupt(boolean)} is invoked with userAction = true
         */
        void onClose(boolean userAction);
    }
}
