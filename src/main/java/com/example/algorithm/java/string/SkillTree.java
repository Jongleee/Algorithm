package com.example.algorithm.java.string;

public class SkillTree {
    public int solution(String skill, String[] skillTrees) {
        int answer = 0;
        for (String skillTree : skillTrees) {
            StringBuilder filteredSkillTree = filterSkillTree(skill, skillTree);
            if (isSkillValid(skill, filteredSkillTree))
                answer++;
        }
        return answer;
    }

    private StringBuilder filterSkillTree(String skill, String skillTree) {
        StringBuilder filteredSkillTree = new StringBuilder();
        for (char c : skillTree.toCharArray()) {
            if (skill.contains(Character.toString(c))) {
                filteredSkillTree.append(c);
            }
        }
        return filteredSkillTree;
    }

    private boolean isSkillValid(String skill, StringBuilder filteredSkillTree) {
        return skill.indexOf(filteredSkillTree.toString()) == 0;
    }
}
