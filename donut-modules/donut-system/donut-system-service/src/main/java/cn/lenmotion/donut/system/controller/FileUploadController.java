package cn.lenmotion.donut.system.controller;

import cn.lenmotion.donut.core.entity.ResponseResult;
import cn.lenmotion.donut.system.entity.covert.FileStorageConverter;
import cn.lenmotion.donut.system.entity.vo.FileUploadVO;
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

/**
 * @author lenmotion
 */
@Slf4j
@RestController
@Tag(name = "文件上传", description = "文件上传")
@RequestMapping("upload")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileStorageService fileStorageService;

    @Operation(summary = "文件上传")
    @PostMapping("file")
    public ResponseResult<FileUploadVO> upload(@RequestPart MultipartFile file,
                                               @Parameter(description = "是否开放访问") @RequestParam(defaultValue = "false") boolean open) {
        FileInfo fileInfo = fileStorageService.of(file).setPath(open ? "open/" : "").upload();
        return ResponseResult.success(FileStorageConverter.INSTANCE.toUploadVO(fileInfo));
    }

    // TODO 文件访问不通过项目去访问，本地文件可以使用nginx代理访问，第三方直接使用第三方的url访问
    @Operation(summary = "文件下载")
    @GetMapping("file/{path}/{name}")
    public void downloadFile(@PathVariable String path, @PathVariable String name, HttpServletResponse response) throws IOException {
        LocalPlusFileStorage storage = fileStorageService.getFileStorage();
        var downloader = fileStorageService.download(storage.getDomain() + path + "/" + name);
        downloader.outputStream(response.getOutputStream());
    }

}
