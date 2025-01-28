package com.de013.model;

import java.io.Serializable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.de013.dto.FilterVO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Paging implements Serializable{
public static final int PAGE = 1;
    public static final int SIZE = 10;

    private String sortColumn = "id";
    private String sortOrder = Direction.ASC.name();

    private int page = PAGE;
    private int size = SIZE;
    private long totalRows = -1;
    private int totalPages = -1;

    public Paging() {

    }

    public Paging (int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if (size >= 1) {
            this.size = size;
        }
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        if (page >= 1) {
            this.page = page;
        }
    }

    public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
	}

	public long getTotalRows() {
		return totalRows;
	}

	public int getTotalPages() {
		return totalPages > 0 ? totalPages : 1;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

    public Pageable getPageRequest(FilterVO request) {
        setPage(request.getPage());
        setSize(request.getSize());
        setSortColumn(request.getSortColumn());
        setSortOrder(request.getSortOrder());

        Sort sort;
        if (this.sortOrder.equals(Direction.ASC.name())) {
            sort = Sort.by(Sort.Order.asc(this.sortColumn));
        } else {
            sort = Sort.by(Sort.Order.desc(this.sortColumn));
        }

        Pageable pageRequest = PageRequest.of(this.page - 1, this.size, sort);
		return pageRequest;
	} 
}
