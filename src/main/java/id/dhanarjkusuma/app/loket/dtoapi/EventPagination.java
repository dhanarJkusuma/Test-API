package id.dhanarjkusuma.app.loket.dtoapi;

import java.util.List;

public class EventPagination {
    private Long totalItem;
    private Integer pageSize;
    private Integer currentPage;
    private List<EventResponse> content;

    public EventPagination() {
    }

    private EventPagination(Builder builder) {
        setTotalItem(builder.totalItem);
        setPageSize(builder.pageSize);
        setCurrentPage(builder.currentPage);
        setContent(builder.content);
    }


    public Long getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Long totalItem) {
        this.totalItem = totalItem;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<EventResponse> getContent() {
        return content;
    }

    public void setContent(List<EventResponse> content) {
        this.content = content;
    }

    public static final class Builder {
        private Long totalItem;
        private Integer pageSize;
        private Integer currentPage;
        private List<EventResponse> content;

        public Builder() {
        }

        public static Builder newBuilder(){
            return new Builder();
        }

        public Builder totalItem(Long val) {
            totalItem = val;
            return this;
        }

        public Builder pageSize(Integer val) {
            pageSize = val;
            return this;
        }

        public Builder currentPage(Integer val) {
            currentPage = val;
            return this;
        }

        public Builder content(List<EventResponse> val) {
            content = val;
            return this;
        }

        public EventPagination build() {
            return new EventPagination(this);
        }
    }
}
