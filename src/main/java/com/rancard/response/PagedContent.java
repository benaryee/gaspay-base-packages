package com.rancard.response;


import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Objects;


public class PagedContent<T> {

    private long totalElements;
    private int totalPages;
    private int page;
    private int size;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    private boolean isFirst;
    private boolean isLast;
    private List<T> data;

    public PagedContent(Page pagedData, List<T> data) {
        this.setData(data);
        this.setTotalElements(pagedData.getTotalElements());
        this.setTotalPages(pagedData.getTotalPages());
        this.setPage(pagedData.getPageable().getPageNumber());
        this.setSize(pagedData.getPageable().getPageSize());
        this.setHasNextPage(pagedData.hasNext());
        this.setHasPreviousPage(pagedData.hasPrevious());
        this.setFirst(pagedData.isFirst());
        this.setLast(pagedData.isLast());
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PagedContent<?> that = (PagedContent<?>) o;
        return totalElements == that.totalElements && totalPages == that.totalPages && page == that.page && size == that.size && hasNextPage == that.hasNextPage && hasPreviousPage == that.hasPreviousPage && isFirst == that.isFirst && isLast == that.isLast && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalElements, totalPages, page, size, hasNextPage, hasPreviousPage, isFirst, isLast, data);
    }

    @Override
    public String toString() {
        return "PagedContent{" +
                "totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                ", page=" + page +
                ", size=" + size +
                ", hasNextPage=" + hasNextPage +
                ", hasPreviousPage=" + hasPreviousPage +
                ", isFirst=" + isFirst +
                ", isLast=" + isLast +
                ", data=" + data +
                '}';
    }
}