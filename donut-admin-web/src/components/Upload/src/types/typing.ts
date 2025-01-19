import { BasicColumn } from '@/components/Table';
import { UploadApiResult } from '@/api/system/model/uploadModel';

export enum UploadResultStatus {
  DONE = 'done',
  SUCCESS = 'success',
  ERROR = 'error',
  UPLOADING = 'uploading',
}

export interface FileItem {
  thumbUrl?: string;
  url?: string;
  name: string;
  size: string | number;
  ext?: string;
  percent?: number;
  file?: File;
  status?: UploadResultStatus;
  response?: UploadApiResult;
  uid: string;
}

export interface PreviewFileInfo {
  ext?: string;
  name?: string;
  uid?: string;
  url?: string;
  size?: number;
  thumbUrl?: string;
  status?: UploadResultStatus;
  percent?: number;
}

export interface PreviewFileItem {
  url: string;
  name: string;
  type: string;
}

export interface FileBasicColumn extends Omit<BasicColumn, 'customRender'> {
  /**
   * Renderer of the table cell. The return value should be a VNode, or an object for colSpan/rowSpan config
   * @type Function | ScopedSlot
   */
  customRender?: Function;
  /**
   * Title of this column
   * @type any (string | slot)
   */
  title: string;

  /**
   * Display field of the data record, could be set like a.b.c
   * @type string
   */
  dataIndex: string;
}
