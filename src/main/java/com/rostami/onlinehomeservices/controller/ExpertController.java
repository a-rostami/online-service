package com.rostami.onlinehomeservices.controller;
import com.rostami.onlinehomeservices.controller.api.filter.ExpertSpecification;
import com.rostami.onlinehomeservices.dto.api.ResponseResult;
import com.rostami.onlinehomeservices.dto.api.auth.in.JwtRequestParam;
import com.rostami.onlinehomeservices.dto.api.filter.ExpertFilter;
import com.rostami.onlinehomeservices.dto.in.create.ExpertCreateParam;
import com.rostami.onlinehomeservices.dto.in.update.ExpertUpdateParam;
import com.rostami.onlinehomeservices.dto.in.update.api.DepositParam;
import com.rostami.onlinehomeservices.dto.in.update.api.PasswordUpdateParam;
import com.rostami.onlinehomeservices.dto.out.CreateUpdateResult;
import com.rostami.onlinehomeservices.dto.out.single.CreditFindResult;
import com.rostami.onlinehomeservices.dto.out.single.ExpertFindResult;
import com.rostami.onlinehomeservices.service.ExpertService;
import com.rostami.onlinehomeservices.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/experts")
@RequiredArgsConstructor
public class ExpertController {
    private final ExpertService expertService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> signup(@Validated @ModelAttribute ExpertCreateParam param) {
        param.setPassword(bCryptPasswordEncoder.encode(param.getPassword()));
        String token = generateToken(param.getEmail(), param.getPassword());

        CreateUpdateResult result = expertService.save(param);

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
    public ResponseEntity<ResponseResult<CreateUpdateResult>> update(@Validated @ModelAttribute ExpertUpdateParam param){
        CreateUpdateResult result = expertService.update(param, param.getId());
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
        ExpertFindResult result = (ExpertFindResult) expertService.findById(id);
        return ResponseEntity.ok(ResponseResult.<ExpertFindResult>builder()
                .code(0)
                .message("Successfully Found Expert.")
                .data(result).build());
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<Set<ExpertFindResult>>> readAll(@RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        Set<ExpertFindResult> results = expertService.findAll(pageable)
                .stream().map(expertOutDto -> (ExpertFindResult) expertOutDto).collect(Collectors.toSet());
        return ResponseEntity.ok(ResponseResult.<Set<ExpertFindResult>>builder()
                .code(0)
                .message("Successfully Found All Experts.")
                .data(results)
                .build());
    }

    @PutMapping("/change-password")
    @PreAuthorize("hasAnyRole('ADMIN', 'EXPERT')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> changePassword(@RequestBody @Valid PasswordUpdateParam param){
        String newPassword = param.getNewPassword();

        param.setNewPassword(bCryptPasswordEncoder.encode(newPassword));

        CreateUpdateResult result = expertService.changePassword(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                builder().code(0).message("Expert Password Successfully Updated.").data(result).build());
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
    @PreAuthorize("hasAnyRole('EXPERT', 'ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> depositToCredit(@RequestBody DepositParam param){
        CreateUpdateResult result = expertService.depositToCredit(param.getUserId(), param.getAmount());
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Successfully Deposit To Credit")
                .data(result)
                .build());
    }

    @PostMapping("/add-sub-serv")
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
    public ResponseEntity<ResponseResult<Set<ExpertFindResult>>> getCustomers(@RequestBody ExpertFilter filter){
        Set<ExpertFindResult> results = expertService.findAll(new ExpertSpecification().getUsers(filter))
                .stream().map(expertOutDto -> (ExpertFindResult) expertOutDto).collect(Collectors.toSet());
        return ResponseEntity.ok(ResponseResult.<Set<ExpertFindResult>>builder()
                .code(0)
                .message("Successfully Load Experts Based Filters.")
                .data(results)
                .build());
    }

    @GetMapping("/count-of-done-ads/{expertId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<Long>> countOfDoneAds(@PathVariable Long expertId){
        long count = expertService.getNumberOfDoneAds(expertId);
        return ResponseEntity.ok(ResponseResult.<Long>builder()
                .code(0)
                .message("Successfully Load Number Of Done Ads By Given Expert.")
                .data(count)
                .build());
    }

    @PutMapping("/final-confirm")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> finalConfirm(@RequestParam String email){
        CreateUpdateResult result = expertService.unlockExpert(email);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Successfully Unlocked Expert.")
                .data(result)
                .build());
    }
}