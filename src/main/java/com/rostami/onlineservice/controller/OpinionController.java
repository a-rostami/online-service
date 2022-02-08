package com.rostami.onlineservice.controller;

import com.rostami.onlineservice.dto.api.ResponseResult;
import com.rostami.onlineservice.dto.in.create.OpinionCreateParam;
import com.rostami.onlineservice.dto.in.update.OpinionUpdateParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.OpinionFindResult;
import com.rostami.onlineservice.model.Expert;
import com.rostami.onlineservice.model.Opinion;
import com.rostami.onlineservice.service.OpinionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/opinions")
@RequiredArgsConstructor
@RestController
public class OpinionController {
    private final OpinionService opinionService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> create(@Validated @RequestBody OpinionCreateParam param){
        CreateUpdateResult result = opinionService.submitOpinion(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Opinion Successfully Created.")
                .data(result)
                .build());
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> update(@Validated @RequestBody OpinionUpdateParam param){
        Opinion fetchedEntity = opinionService.getForUpdate(param.getId());
        Opinion updatedEntity = param.convertToDomain(fetchedEntity);
        CreateUpdateResult result = opinionService.update(updatedEntity);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                builder().code(0).message("Opinion Successfully Updated.").data(result).build());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> delete(@Validated @PathVariable Long id){
        opinionService.delete(id);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                        builder().code(0).message("Opinion Successfully Deleted.")
                .data(CreateUpdateResult.builder().success(true).id(id).build()).build());
    }

    @GetMapping("/load/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<OpinionFindResult>> read(@Validated @PathVariable Long id){
        OpinionFindResult result = (OpinionFindResult) opinionService.get(id);
        return ResponseEntity.ok(ResponseResult.<OpinionFindResult>builder()
                .code(0)
                .message("Successfully Found Opinion.")
                .data(result).build());
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'EXPERT', 'CUSTOMER')")
    public ResponseEntity<ResponseResult<List<OpinionFindResult>>> readAll(@RequestParam Integer page, @RequestParam Long expertId){
        Expert expert = Expert.builder().id(expertId).build();
        Pageable pageable = PageRequest.of(page, 5);
        List<OpinionFindResult> results = opinionService.findAll((root, query, cb) ->
                        cb.equal(root.get("expert"), expert), pageable)
                .stream().map(opinionOutDto -> (OpinionFindResult) opinionOutDto).toList();
        return ResponseEntity.ok(ResponseResult.<List<OpinionFindResult>>builder()
                .code(0)
                .message("Successfully Found All Opinions.")
                .data(results)
                .build());
    }
}
