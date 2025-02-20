package cn.lenmotion.donut.system.controller;

import cn.lenmotion.donut.core.entity.ResponseResult;
import cn.lenmotion.donut.system.entity.converter.FileStorageConverter;
import cn.lenmotion.donut.system.entity.vo.FileUploadVO;
import cn.lenmotion.donut.system.service.SysFileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.dromara.x.file.storage.core.platform.LocalPlusFileStorage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author lenmotion
 */
@Slf4j
@RestController
@Tag(name = "文件上传", description = "文件上传")
@RequestMapping("/system")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileStorageService fileStorageService;
    private final SysFileStorageService sysFileStorageService;

    @Operation(summary = "文件上传")
    @PostMapping("/upload/file")
    public ResponseResult<FileUploadVO> upload(@RequestPart MultipartFile file,
                                               @Parameter(description = "是否开放访问") @RequestParam(defaultValue = "false") boolean open) {
        var fileInfo = fileStorageService.of(file).setPath(open ? "open/" : "").upload();
        return ResponseResult.success(FileStorageConverter.INSTANCE.toUploadVO(fileInfo));
    }

    @GetMapping("fileInfo/{ids}")
    public ResponseResult<List<FileUploadVO>> info(@PathVariable("ids") List<String> ids) {
        var files = sysFileStorageService.listByIds(ids);
        return ResponseResult.success(FileStorageConverter.INSTANCE.toUploadVO(files));
    }

    // TODO 文件访问不通过项目去访问，本地文件可以使用nginx代理访问，第三方直接使用第三方的url访问
    @Operation(summary = "文件下载")
    @GetMapping("download/file/{path}/{name}")
    public void downloadFile(@PathVariable String path, @PathVariable String name, HttpServletResponse response) throws IOException {
        LocalPlusFileStorage storage = fileStorageService.getFileStorage();
        var downloader = fileStorageService.download(storage.getDomain() + path + "/" + name);
        downloader.outputStream(response.getOutputStream());
    }

}
