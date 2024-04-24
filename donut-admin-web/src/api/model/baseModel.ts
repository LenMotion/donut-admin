export interface PageResult<T> {
  pageNum: number;
  pageSize: number;
  total: number;
  items: T[];
}
