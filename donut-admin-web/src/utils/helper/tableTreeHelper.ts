import { TableActionType } from '@/components/Table';

export interface OptParams {
  item: Recordable;
  orderField: string;
  isUpdate: Boolean;
  tableAction: TableActionType;
}

/**
 * 插入数据参数
 * @param params
 * @returns
 */
export const insertTableData = (params: OptParams) => {
  const { item, orderField, isUpdate, tableAction } = params;
  const { insertTableDataRecord, updateTableDataRecord, getDataSource, findTableDataRecord } =
    tableAction;
  if (isUpdate) {
    // 更新数据
    updateTableDataRecord(item.id, item);
    // 因为可能设置到更新排序，所以需要重新排序
    if (item.parentId == 0) {
      getDataSource().sort((a, b) => a[orderField] - b[orderField]);
    } else {
      const parentItem = findTableDataRecord(item.parentId);
      parentItem!.children.sort((a, b) => a[orderField] - b[orderField]);
      updateTableDataRecord(parentItem!.id, parentItem!);
    }
  } else {
    // 如果上级的id为0，表示在第一级插入数据
    if (item.parentId == 0) {
      const treeList = getDataSource() || [];
      // 如果当前的序号大于等于某个菜单的序号，那么就插入到这个菜单的后面
      const index = treeList.findIndex((record) => item[orderField] <= record[orderField]);
      insertTableDataRecord(
        item,
        treeList[index][orderField] == item[orderField] ? index + 1 : index,
      );
    } else {
      // 根据上级的id获取对应的数据
      const parentItem = findTableDataRecord(item.parentId);
      if (!parentItem) {
        return;
      }
      // 拿到对应的子菜单
      const children = parentItem.children || [];
      // 查询比插入数据要大的坐标
      const index = children.findIndex((record) => item[orderField] <= record[orderField]);
      // 如果没有找到比item.orderNo大的元素，直接插入到末尾
      if (index === -1) {
        children.push(item);
      } else {
        children.splice(
          children[index][orderField] == item[orderField] ? index + 1 : index,
          0,
          item,
        );
      }
      // 更新数据
      parentItem.children = children;
      updateTableDataRecord(parentItem.id, parentItem);
    }
  }
};
