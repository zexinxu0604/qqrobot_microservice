package org.xzx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xzx.bean.Domain.AICharacter;
import org.xzx.dao.AICharacterDao;

import java.util.List;

@Service
public class AICharacterService {

    @Autowired
    private AICharacterDao aiCharacterDao;

    public AICharacter getAICharacterByDesc(String desc) {
        QueryWrapper<AICharacter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("character_desc", desc);
        return aiCharacterDao.selectOne(queryWrapper);
    }

    public List<AICharacter> getAllAICharacters() {
        QueryWrapper<AICharacter> queryWrapper = new QueryWrapper<>();
        return aiCharacterDao.selectList(queryWrapper);
    }

    public boolean insertAICharacter(AICharacter aiCharacter) {
        return aiCharacterDao.insert(aiCharacter) == 1;
    }
}
