package com.project.devgram.service;

import com.project.devgram.dto.CategoryDto;
import com.project.devgram.dto.ProductDto;
import java.util.List;
import java.util.Optional;

public interface IProductService {

	boolean write(ProductDto parameter); // 글 작성

	List<ProductDto> confirm(); // admin 제품관리

	List<ProductDto> list(); // 전체 list(Approve)

	boolean update(ProductDto parameter); // product 업데이트

	boolean delete(long id);

	List<ProductDto> popularList(); // list(Approve) 인기순

}
