/*******************************************************************************
 * Copyright (C) 2011 Angelo Zerr <angelo.zerr@gmail.com>, Pascal Leclercq <pascal.leclercq@gmail.com>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo ZERR - initial API and implementation
 *     Pascal Leclercq - initial API and implementation
 *******************************************************************************/
package org.eclipse.nebula.widgets.pagination.collections;

import java.util.List;

import org.eclipse.nebula.widgets.pagination.PageableController;
import org.eclipse.swt.SWT;

/**
 * Helper to create implementation of {@link PageResult} from a Java
 * {@link List}.
 * 
 */
public class PageListHelper {

	public static PageResult<?> createPage(List<?> list,
			PageableController controller) {
		return createPage(list, controller, DefaultSortProcessor.getInstance());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static PageResult<?> createPage(List<?> list,
			PageableController controller, SortProcessor processor) {
		int sortDirection = controller.getSortDirection();
		if (sortDirection != SWT.NONE) {
			// Sort the list
			processor.sort(list, controller.getSortPropertyName(),
					sortDirection);
		}
		int totalSize = list.size();
		int pageSize = controller.getPageSize();
		int pageIndex = controller.getPageOffset();

		int fromIndex = pageIndex;
		int toIndex = pageIndex + pageSize;
		if (toIndex > totalSize) {
			toIndex = totalSize;
		}
		List<?> content = list.subList(fromIndex, toIndex);
		return new PageResult(content, totalSize);
	}

}