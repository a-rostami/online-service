package com.rostami.onlineservice.controller;

import com.rostami.onlineservice.controller.api.filter.CustomerSpecification;
import com.rostami.onlineservice.dto.api.ResponseResult;
import com.rostami.onlineservice.dto.api.filter.CustomerFilter;
import com.rostami.onlineservice.dto.in.create.CustomerCreateParam;
import com.rostami.onlineservice.dto.in.update.CustomerUpdateParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.CustomerFindResult;
import com.rostami.onlineservice.entity.Customer;
import com.rostami.onlineservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/filter")
    public ResponseEntity<ResponseResult<List<CustomerFindResult>>> getCustomers(@RequestBody CustomerFilter filter){
        List<Customer> all = customerService.findAll(CustomerSpecification.getCustomers(filter));
        List<CustomerFindResult> results =
                all.stream().map(p -> CustomerFindResult.builder().build().convertToDto(p)).collect(Collectors.toList());
        return ResponseEntity.ok(ResponseResult.<List<CustomerFindResult>>builder()
                .code(0)
                .message("Successfully Load Customers Based Filters.")
                .data(results)
                .build());
    }

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
    public ResponseEntity<ResponseResult<List<CustomerFindResult>>> readAll(){
        List<CustomerFindResult> results = customerService.list()
                .stream().map((baseOutDto) -> (CustomerFindResult) baseOutDto).collect(Collectors.toList());
        return ResponseEntity.ok(ResponseResult.<List<CustomerFindResult>>builder()
                .code(0)
                .message("All Customers Loaded Successfully.")
                .data(results)
                .build());
    }
}
