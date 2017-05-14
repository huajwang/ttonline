package com.troika.emall.util;

import java.util.List;

public class Page<T> {
	private static final int PAGE_SIZE = 1;
	private static final int PAGE_NO = 1;
    //结果集  
    private List<T> pageResult;
    //查询记录数  
    private long totalRecords;
    //每页多少条记录  
    private int pageSize=PAGE_SIZE;
    //第几页  
    private int pageNo=PAGE_NO;
    
    public long getTotalRecords() {
        return totalRecords;
    }
    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getPageNo() {
        return pageNo;
    }
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    /** 
     * 总页数 
     * @return 
     */  
    public int getTotalPages(){
        return (int) ((totalRecords+pageSize-1)/pageSize);
    }  
    /** 
     * 取得首页 
     * @return 
     */  
    public int getTopPageNo(){
        return 1;
    }
    /** 
     * 上一页 
     * @return 
     */  
    public int getPreviousPageNo(){
        if(pageNo<=1){
            return 1;
        }
        return pageNo-1;
    }
    /** 
     * 下一页 
     * @return 
     */  
    public int getNextPageNo(){
        if (pageNo >= getBottomPageNo()) {
            return getBottomPageNo();
        }
        return pageNo + 1;
    }
    /** 
     * 取得尾页 
     * @return 
     */  
    public int getBottomPageNo(){
        return getTotalPages();
    }
	public List<T> getPageResult() {
		return pageResult;
	}
	public void setPageResult(List<T> pageResult) {
		this.pageResult = pageResult;
	}
  
}  