package com.rostami.onlineservice.controller;
import com.rostami.onlineservice.controller.api.filter.ExpertSpecification;
import com.rostami.onlineservice.dto.api.ResponseResult;
import com.rostami.onlineservice.dto.api.auth.in.JwtRequestParam;
import com.rostami.onlineservice.dto.api.filter.ExpertFilter;
import com.rostami.onlineservice.dto.in.create.CustomerCreateParam;
import com.rostami.onlineservice.dto.in.create.ExpertCreateParam;
import com.rostami.onlineservice.dto.in.update.ExpertUpdateParam;
import com.rostami.onlineservice.dto.in.update.api.DepositParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.CreditFindResult;
import com.rostami.onlineservice.dto.out.single.ExpertFindResult;
import com.rostami.onlineservice.service.ExpertService;
import com.rostami.onlineservice.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/experts")
@RequiredArgsConstructor
public class ExpertController {
    private final ExpertService expertService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> signup(@Validated @RequestBody ExpertCreateParam param) {
        param.setPassword(bCryptPasswordEncoder.encode(param.getPassword()));
        String token = generateToken(param.getEmail(), param.getPassword());

        CreateUpdateResult result = expertService.saveOrUpdate(param);

        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Expert Successfully Registered. Token : " + token)
                .data(result)
                .build());
    }

    private String generateToken(String email, String password) {
        return jwtTokenUtil.generateAccessToken(
                JwtRequestParam.builder()
                        .email(email)
                        .password(password)
                        .build());
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('EXPERT', 'ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> update(@Validated @RequestBody ExpertUpdateParam param){
        param.setPassword(bCryptPasswordEncoder.encode(param.getPassword()));
        CreateUpdateResult result = expertService.saveOrUpdate(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                builder().code(0).message("Expert Successfully Updated.").data(result).build());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('EXPERT', 'ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> delete(@Validated @PathVariable Long id){
        expertService.delete(id);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                        builder().code(0).message("Expert Successfully Deleted.")
                .data(CreateUpdateResult.builder().success(true).id(id).build()).build());
    }

    @GetMapping("/load/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EXPERT', 'ADMIN')")
    public ResponseEntity<ResponseResult<ExpertFindResult>> read(@Validated @PathVariable Long id){
        ExpertFindResult result = (ExpertFindResult) expertService.get(id);
        return ResponseEntity.ok(ResponseResult.<ExpertFindResult>builder()
                .code(0)
                .message("Successfully Found Expert.")
                .data(result).build());
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<List<ExpertFindResult>>> readAll(@RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        List<ExpertFindResult> results = expertService.findAll(pageable)
                .stream().map(expertOutDto -> (ExpertFindResult) expertOutDto).toList();
        return ResponseEntity.ok(ResponseResult.<List<ExpertFindResult>>builder()
                .code(0)
                .message("Successfully Found All Experts.")
                .data(results)
                .build());
    }

    @GetMapping("/credit/{id}")
    @PreAuthorize("hasAnyRole('EXPERT', 'ADMIN')")
    public ResponseEntity<ResponseResult<CreditFindResult>> loadCredit(@PathVariable Long id){
        CreditFindResult creditFindResult = expertService.loadCredit(id);
        return ResponseEntity.ok(ResponseResult.<CreditFindResult>builder()
                .code(0)
                .message("Successfully Load Credit.")
                .data(creditFindResult)
                .build());
    }

    @PutMapping("/deposit")
    @PreAuthorize("hasAnyRole('EXPERT')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> depositToCredit(@RequestBody DepositParam param){
        CreateUpdateResult result = expertService.depositToCredit(param.getUserId(), param.getAmount());
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Successfully Deposit To Credit")
                .data(result)
                .build());
    }

    @PostMapping("/addSubServ")
    @PreAuthorize("hasAnyRole('EXPERT', 'ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> chooseExpert(@RequestParam Long expertId, @RequestParam Long subServId){
        CreateUpdateResult result = expertService.addSubServ(expertId, subServId);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("SubServ Successfully Added To Expert SubServs.")
                .data(result)
                .build());
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<List<ExpertFindResult>>> getCustomers(@RequestBody ExpertFilter filter){
        List<ExpertFindResult> results = expertService.findAll(new ExpertSpecification().getUsers(filter))
                .stream().map(expertOutDto -> (ExpertFindResult) expertOutDto).toList();
        return ResponseEntity.ok(ResponseResult.<List<ExpertFindResult>>builder()
                .code(0)
                .message("Successfully Load Experts Based Filters.")
                .data(results)
                .build());
    }

    @GetMapping("/countOfDoneAds/{expertId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<Long>> countOfDoneAds(@PathVariable Long expertId){
        long count = expertService.getNumberOfDoneAds(expertId);
        return ResponseEntity.ok(ResponseResult.<Long>builder()
                .code(0)
                .message("Successfully Load Number Of Done Ads By Given Expert.")
                .data(count)
                .build());
    }
}