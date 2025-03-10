package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.BlogDTO;
import com.swd.pregnancycare.entity.BlogEntity;
import com.swd.pregnancycare.entity.GroupEntity;
import com.swd.pregnancycare.entity.UserEntity;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.repository.BlogRepo;
import com.swd.pregnancycare.repository.UserRepo;
import com.swd.pregnancycare.request.BlogRequest;
import com.swd.pregnancycare.request.GroupRequest;
import com.swd.pregnancycare.response.BaseResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImp implements BlogServices {
  @Autowired
  private BlogRepo blogRepo;
  @Autowired
  private UserRepo userRepo;

  @Transactional
  @Override
  public void saveBlog(BlogRequest blog) {
    Optional<UserEntity> user = userRepo.findByEmail(blog.getEmail());
    if (user.isEmpty()) throw new AppException(ErrorCode.USER_NOT_EXIST);
    if (user.isPresent()) {
      UserEntity userEntity = user.get();
      BlogEntity newBlog = new BlogEntity();

      newBlog.setTitle(blog.getTitle());
      newBlog.setDescription(blog.getDescription());
      newBlog.setStatus(false);
      newBlog.setDatePublish(LocalDateTime.now());
      newBlog.setUser(userEntity);
      blogRepo.save(newBlog);
    } else throw new AppException(ErrorCode.BLOG_SAVED_EXCEPTION);
  }


  @Transactional
  @Override
  @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
  public List<BlogDTO> getAllBlogs() {
    return blogRepo.findAll().stream().map(data -> {
      BlogDTO blogDTO = new BlogDTO();
      blogDTO.setId(data.getId());
      blogDTO.setTitle(data.getTitle());
      blogDTO.setDescription(data.getDescription());
      blogDTO.setDatePublish(data.getDatePublish());
      blogDTO.setStatus(data.getStatus());
      if (data.getUser() != null) {
        blogDTO.setUserId(data.getUser().getId());
      }
      return blogDTO;
    }).toList();
  }

  @Transactional
  @Override
  public void deleteBlog(int id) {
    Optional<BlogEntity> blog = blogRepo.findById(id);
    if (blog.isEmpty()) throw new AppException(ErrorCode.BLOG_NOT_EXIST);
    blogRepo.deleteById(blog.get().getId());
  }

  @Transactional
  @Override
  public void updateBlog(BlogRequest blogRequest, int id) {
    BlogEntity blogEntity = blogRepo.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_EXIST));

    blogEntity.setTitle(blogRequest.getTitle());
    blogEntity.setDescription(blogRequest.getDescription());
    blogEntity.setDatePublish(LocalDateTime.now());

    blogRepo.save(blogEntity);
  }
}
