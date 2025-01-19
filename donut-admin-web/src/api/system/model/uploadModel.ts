export interface UploadApiResult {
  msg: string;
  code: number;
  result: FileInfo;
}

export interface FileInfo {
  ext?: string;
  name?: string;
  uid?: string;
  url?: string;
  size?: number;
}
