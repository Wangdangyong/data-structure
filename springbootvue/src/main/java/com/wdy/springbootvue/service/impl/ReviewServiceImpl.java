package com.wdy.springbootvue.service.impl;

import com.wdy.springbootvue.entity.Review;
import com.wdy.springbootvue.mapper.ReviewMapper;
import com.wdy.springbootvue.service.ReviewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 王当勇
 * @since 2023-04-22
 */
@Service
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements ReviewService {

}
