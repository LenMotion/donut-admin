import { UploadApiResult, FileInfo } from './model/uploadModel';
import { defHttp } from '@/utils/http/axios';
import { UploadFileParams } from '#/axios';
import { useGlobSetting } from '@/hooks/setting';
import { AxiosProgressEvent } from 'axios';
import { isArray } from '@/utils/is';

const { uploadUrl = '' } = useGlobSetting();

/**
 * @description: Upload interface
 */
export function uploadApi(
  params: UploadFileParams,
  onUploadProgress?: (progressEvent: AxiosProgressEvent) => void,
) {
  return defHttp.uploadFile<UploadApiResult>(
    {
      url: uploadUrl,
      onUploadProgress,
    },
    params,
  );
}

export const fileInfoApi = (ids: string | string[]) => {
  return defHttp.get<FileInfo[]>({
    url: '/system/fileInfo/' + (isArray(ids) ? ids.join(',') : ids),
  });
};
