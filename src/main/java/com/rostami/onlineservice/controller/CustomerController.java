package com.rostami.onlineservice.controller;

import com.rostami.onlineservice.controller.api.filter.CustomerSpecification;
import com.rostami.onlineservice.dto.api.ResponseResult;
import com.rostami.onlineservice.dto.api.auth.in.JwtRequestParam;
import com.rostami.onlineservice.dto.api.filter.CustomerFilter;
import com.rostami.onlineservice.dto.in.create.CustomerCreateParam;
import com.rostami.onlineservice.dto.in.update.CustomerUpdateParam;
import com.rostami.onlineservice.dto.in.update.api.DepositParam;
import com.rostami.onlineservice.dto.in.update.api.PurchaseParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.UserCreateResult;
import com.rostami.onlineservice.dto.out.single.CreditFindResult;
import com.rostami.onlineservice.dto.out.single.CustomerFindResult;
import com.rostami.onlineservice.service.CustomerService;
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
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final JwtTokenUtil jwtTokenUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseResult<UserCreateResult>> signup(@Validated @RequestBody CustomerCreateParam param) {
        param.setPassword(bCryptPasswordEncoder.encode(param.getPassword()));

        // generating jwt token
        String token = jwtTokenUtil.generateAccessToken(
                JwtRequestParam.builder()
                        .email(param.getEmail())
                        .password(param.getPassword())
                        .build());

        CreateUpdateResult createUpdateResult = customerService.saveOrUpdate(param);
        UserCreateResult result = UserCreateResult.builder()
                .id(createUpdateResult.getId())
                .success(createUpdateResult.getSuccess())
                .token(token)
                .build();

        return ResponseEntity.ok(ResponseResult.<UserCreateResult>builder()
                .code(0)
                .message("Customer Successfully Registered.")
                .data(result)
                .build());
    }


    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> update(@Validated @RequestBody CustomerUpdateParam param) {
        param.setPassword(bCryptPasswordEncoder.encode(param.getPassword()));
        CreateUpdateResult result = customerService.saveOrUpdate(param);
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
    public ResponseEntity<ResponseResult<List<CustomerFindResult>>> readAll(@RequestParam Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        List<CustomerFindResult> results = customerService.findAll(pageable)
                .stream().map(customerOutDto -> (CustomerFindResult) customerOutDto).toList();
        return ResponseEntity.ok(ResponseResult.<List<CustomerFindResult>>builder()
                .code(0)
                .message("All Customers Loaded Successfully.")
                .data(results)
                .build());
    }

    @PutMapping("/deposit")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> depositToCredit(@RequestBody DepositParam param) {
        CreateUpdateResult result = customerService.depositToCredit(param.getUserId(), param.getAmount());
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Successfully Deposit To Credit")
                .data(result)
                .build());
    }

    @PutMapping("/purchase")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
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
    public ResponseEntity<ResponseResult<List<CustomerFindResult>>> getCustomers(@RequestBody CustomerFilter filter, @RequestParam Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        List<CustomerFindResult> results = customerService.findAll(new CustomerSpecification().getUsers(filter), pageable)
                .stream().map(customerOutDto -> (CustomerFindResult) customerOutDto).toList();
        return ResponseEntity.ok(ResponseResult.<List<CustomerFindResult>>builder()
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
}
