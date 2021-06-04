package com.work.app.serviceimpl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;
import com.work.app.jparepository.EmployeeJpaRepo;
import com.work.app.model.Employee;
import com.work.app.repository.EmployeeRepo;
import com.work.app.service.EmployeeService;

import io.minio.MinioClient;
import io.minio.messages.Bucket;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	EmployeeJpaRepo repo;
	
	@Autowired
	EmployeeRepo epo;
	
	@Autowired
	MinioClient minioClient;
	
	@Value("${spring.minio.bucket}")
    String bucket;
	
	
    private final ElasticsearchOperations elasticsearchOperations;
	
	private final MinioService minioService;
	
	
	public EmployeeServiceImpl(ElasticsearchOperations elasticsearchOperations, MinioService minioService) {
		super();
		this.elasticsearchOperations = elasticsearchOperations;
		this.minioService = minioService;
	}

	@Override
	public List<Employee> getAllEmployee() {
		List<Employee> target = new ArrayList<>();
		repo.findAll().forEach(target::add);
		return target;
		
	}

	@Override
	public Employee getById(Long id) {
		BoolQueryBuilder searchQuery = buildBoolQueryBuilder(id);
		NativeSearchQuery nativeSearchQueryBuilder = new NativeSearchQueryBuilder()
                .withQuery(searchQuery)
                .withSort(SortBuilders.fieldSort("createdDate").order(SortOrder.DESC))
                .withPageable(pageable)
                .build();
		 Employee userActivity = elasticsearchOperations.queryForPage(nativeSearchQueryBuilder, Employee.class);
		System.out.println("Hello");
		return repo.findById(id).get();
	}

	@Override
	public void deleteEmployee(Long id) {
		repo.deleteById(id);
		
	}

	@Override
	public void addEmployee(Employee employee) {
		repo.save(employee);
	}

	public void uploadFile(MultipartFile file) {
		String fileName = "photo/" + file.getOriginalFilename();
		Path path = Paths.get(fileName);
		try {
			minioService.upload(path, file.getInputStream(), file.getContentType());
		} catch (MinioException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Bucket> getAllBuckests() {
		try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
	}

}
