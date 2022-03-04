package com.rostami.onlinehomeservices.controller;

import com.rostami.onlinehomeservices.controller.api.filter.CustomerSpecification;
import com.rostami.onlinehomeservices.dto.api.ResponseResult;
import com.rostami.onlinehomeservices.dto.api.auth.in.JwtRequestParam;
import com.rostami.onlinehomeservices.dto.api.filter.CustomerFilter;
import com.rostami.onlinehomeservices.dto.in.create.CustomerCreateParam;
import com.rostami.onlinehomeservices.dto.in.update.CustomerUpdateParam;
import com.rostami.onlinehomeservices.dto.in.update.api.DepositParam;
import com.rostami.onlinehomeservices.dto.in.update.api.PasswordUpdateParam;
import com.rostami.onlinehomeservices.dto.in.update.api.PurchaseParam;
import com.rostami.onlinehomeservices.dto.out.CreateUpdateResult;
import com.rostami.onlinehomeservices.dto.out.single.CreditFindResult;
import com.rostami.onlinehomeservices.dto.out.single.CustomerFindResult;
import com.rostami.onlinehomeservices.service.CustomerService;
import com.rostami.onlinehomeservices.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {
    private final CustomerService customerService;
    private final JwtTokenUtil jwtTokenUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> signup(@Validated @RequestBody CustomerCreateParam param) {
        param.setPassword(bCryptPasswordEncoder.encode(param.getPassword()));
        String token = generateToken(param.getEmail(), param.getPassword());
        CreateUpdateResult result = customerService.save(param);

        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Customer Successfully Registered. Token : " + token)
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
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> update(@Validated @RequestBody CustomerUpdateParam param) {
        CreateUpdateResult result = customerService.update(param, param.getId());
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                builder().code(0).message("Customer Successfully Updated.").data(result).build());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> delete(@Validated @PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                        builder().code(0).message("Customer Successfully Deleted.")
                .data(CreateUpdateResult.builder().success(true).id(id).build()).build());
    }

    @GetMapping("/load/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EXPERT', 'ADMIN')")
    public ResponseEntity<ResponseResult<CustomerFindResult>> read(@Validated @PathVariable Long id) {
        CustomerFindResult result = (CustomerFindResult) customerService.get(id);
        return ResponseEntity.ok(ResponseResult.<CustomerFindResult>builder()
                .code(0)
                .message("Customer Successfully Loaded.")
                .data(result)
                .build());
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<Set<CustomerFindResult>>> readAll(@RequestParam Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        Set<CustomerFindResult> results = customerService.findAll(pageable)
                .stream().map(customerOutDto -> (CustomerFindResult) customerOutDto).collect(Collectors.toSet());
        return ResponseEntity.ok(ResponseResult.<Set<CustomerFindResult>>builder()
                .code(0)
                .message("All Customers Loaded Successfully.")
                .data(results)
                .build());
    }

    @PutMapping("/deposit")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> depositToCredit(@RequestBody DepositParam param) {
        CreateUpdateResult result = customerService.depositToCredit(param.getUserId(), param.getAmount());
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Successfully Deposit To Credit")
                .data(result)
                .build());
    }

    @PutMapping("/purchase")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> purchase(@RequestBody PurchaseParam param) {
        CreateUpdateResult result = customerService.purchase(param.getCustomerId(), param.getExpertId(), param.getAmount());
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Successfully Purchased.")
                .data(result)
                .build());
    }

    @GetMapping("/credit/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ResponseResult<CreditFindResult>> loadCredit(@PathVariable Long id) {
        CreditFindResult creditFindResult = customerService.loadCredit(id);
        return ResponseEntity.ok(ResponseResult.<CreditFindResult>builder()
                .code(0)
                .message("Successfully Load Credit.")
                .data(creditFindResult)
                .build());
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<Set<CustomerFindResult>>> getCustomers(@RequestBody CustomerFilter filter, @RequestParam Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        Set<CustomerFindResult> results = customerService.findAll(new CustomerSpecification().getUsers(filter), pageable)
                .stream().map(customerOutDto -> (CustomerFindResult) customerOutDto).collect(Collectors.toSet());
        return ResponseEntity.ok(ResponseResult.<Set<CustomerFindResult>>builder()
                .code(0)
                .message("Successfully Load Customers Based Filters.")
                .data(results)
                .build());
    }

    @GetMapping("/countOfCustomerAds/{customerId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<Long>> countOfAds(@PathVariable Long customerId) {
        long count = customerService.getNumberOfCustomerAds(customerId);
        return ResponseEntity.ok(ResponseResult.<Long>builder()
                .code(0)
                .message("Successfully Found Number Of Customer's Ads.")
                .data(count)
                .build());
    }

    @PutMapping("/changePassword")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> changePassword(@RequestBody @Valid PasswordUpdateParam param){
        String newPassword = param.getNewPassword();
        param.setNewPassword(bCryptPasswordEncoder.encode(newPassword));

        CreateUpdateResult result = customerService.changePassword(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                builder().code(0).message("Customer Password Successfully Updated.").data(result).build());
    }
}
