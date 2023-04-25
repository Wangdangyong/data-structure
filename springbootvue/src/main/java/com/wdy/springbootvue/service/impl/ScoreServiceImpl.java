package com.wdy.springbootvue.service.impl;

import com.wdy.springbootvue.entity.Score;
import com.wdy.springbootvue.mapper.ScoreMapper;
import com.wdy.springbootvue.service.ScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 王当勇
 * @since 2023-04-12
 */
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {

}
