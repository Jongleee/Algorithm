import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestValue {
    public int solution(String skill, String[] skillTrees) {
        int answer = 0;
        for (String skillTree : skillTrees) {
            if (isSkillValid(skill, skillTree)) {
                answer++;
            }
        }
        return answer;
    }

    private boolean isSkillValid(String skill, String skillTree) {
        StringBuilder filteredSkillTree = new StringBuilder();
        for (char c : skillTree.toCharArray()) {
            if (skill.contains(Character.toString(c))) {
                filteredSkillTree.append(c);
            }
        }
        return skill.startsWith(filteredSkillTree.toString());
    }

    @Test
    public void 정답() {
        Assertions.assertEquals(2, solution("CBD", new String[] { "BACDE", "CBADF", "AECB", "BDA" }));
    }
}
