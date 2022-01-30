package com.rostami.onlineservice.controller;

import com.rostami.onlineservice.controller.api.filter.CustomerSpecification;
import com.rostami.onlineservice.dto.api.ResponseResult;
import com.rostami.onlineservice.dto.api.filter.CustomerFilter;
import com.rostami.onlineservice.dto.in.create.CustomerCreateParam;
import com.rostami.onlineservice.dto.in.update.CustomerUpdateParam;
import com.rostami.onlineservice.dto.in.update.api.DepositParam;
import com.rostami.onlineservice.dto.in.update.api.PurchaseParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.AdFindResult;
import com.rostami.onlineservice.dto.out.single.CreditFindResult;
import com.rostami.onlineservice.dto.out.single.CustomerFindResult;
import com.rostami.onlineservice.entity.Ad;
import com.rostami.onlineservice.entity.Customer;
import com.rostami.onlineservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> save(@Validated @RequestBody CustomerCreateParam param){
        CreateUpdateResult result = customerService.saveOrUpdate(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0).message("Customer Successfully Created.").data(result).build());
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> update(@Validated @RequestBody CustomerUpdateParam param){
        CreateUpdateResult result = customerService.saveOrUpdate(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                builder().code(0).message("Customer Successfully Updated.").data(result).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> delete(@Validated @PathVariable Long id){
        customerService.delete(id);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                        builder().code(0).message("Customer Successfully Deleted.")
                .data(CreateUpdateResult.builder().success(true).id(id).build()).build());
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<ResponseResult<CustomerFindResult>> read(@Validated @PathVariable Long id){
        CustomerFindResult result = (CustomerFindResult) customerService.get(id);
        return ResponseEntity.ok(ResponseResult.<CustomerFindResult>builder()
                .code(0)
                .message("Customer Successfully Loaded.")
                .data(result)
                .build());
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseResult<List<CustomerFindResult>>> readAll(@RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        Stream<Customer> stream = customerService.findAll(pageable).get();
        List<CustomerFindResult> results =
                stream.map(customer -> CustomerFindResult.builder().build().convertToDto(customer)).collect(Collectors.toList());
        return ResponseEntity.ok(ResponseResult.<List<CustomerFindResult>>builder()
                .code(0)
                .message("All Customers Loaded Successfully.")
                .data(results)
                .build());
    }

    @PutMapping("/deposit")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> depositToCredit(@RequestBody DepositParam param){
        CreateUpdateResult result = customerService.depositToCredit(param.getUserId(), param.getAmount());
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Successfully Deposit To Credit")
                .data(result)
                .build());
    }

    @PutMapping("/purchase")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> purchase(@RequestBody PurchaseParam param){
        CreateUpdateResult result = customerService.purchase(param.getCustomerId(), param.getExpertId(), param.getAmount());
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Successfully Purchased.")
                .data(result)
                .build());
    }

    @GetMapping("/credit/{id}")
    public ResponseEntity<ResponseResult<CreditFindResult>> loadCredit(@PathVariable Long id){
        CreditFindResult creditFindResult = customerService.loadCredit(id);
        return ResponseEntity.ok(ResponseResult.<CreditFindResult>builder()
                .code(0)
                .message("Successfully Load Credit.")
                .data(creditFindResult)
                .build());
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseResult<List<CustomerFindResult>>> getCustomers(@RequestBody CustomerFilter filter, @RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        List<Customer> all = customerService.findAll(new CustomerSpecification().getUsers(filter), pageable);
        List<CustomerFindResult> results =
                all.stream().map(p -> CustomerFindResult.builder().build().convertToDto(p)).collect(Collectors.toList());
        return ResponseEntity.ok(ResponseResult.<List<CustomerFindResult>>builder()
                .code(0)
                .message("Successfully Load Customers Based Filters.")
                .data(results)
                .build());
    }

    @GetMapping("/countOfRelatedAds/{customerId}")
    public ResponseEntity<ResponseResult<Long>> countOfAds(@PathVariable Long customerId){
        long count = customerService.getNumberOfRelatedAds(customerId);
        return ResponseEntity.ok(ResponseResult.<Long>builder()
                .code(0)
                .message("Successfully Found Number Of Customer's Ads.")
                .data(count)
                .build());
    }
}
