package community.member.service;

import community.member.entity.Level;
import community.member.entity.Member;
import community.member.repository.LevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class LevelService {
    private final LevelRepository levelRepository;

    /*회원 레벨*/
    public Level memberlevel(Member member) {
        Level level = findLevel(member.getMemberId()); //회원 레벨정보 가져오기
        gainExperience(member, level); // 경험치 정보 업데이트

        int myLevel = level.getLevel();

        /*만렙은 20
        레벨업을 할때마다 필요 경험치량이 늘어난다.*/
        if ((myLevel < 20) && (level.getTotalExp() >= level.getRequiredExp())) { //다음 레벨까지의 필요경험치 달성시
            level.setLevel(myLevel+1);//레벨업
            level.setRequiredExp(level.getRequiredExp() + (level.getLevel() * 20 + 100)); // 다음레벨에 필요한 경험치 총량상승
            setLevelExp(level); //경험치% 설정 및 저장
        }
        else if (myLevel < 20) { //다음 레벨까지의 필요경험치 미달
            setLevelExp(level);
        }
        else { //만렙 달성시
            level.setLevelExp(0);
        }
        return level;
    }

    /*경험치 획득*/
    public void gainExperience(Member member, Level level) { 
        // 게시글은 경험치 10, 댓글은 경험치 3을 부여한다.
        level.setTotalExp((member.getBoardCount() * 10) + member.getCommentCount() * 3);
    }

    /*경험치% 계산
    * 백분율 계산을 위해 double로 선언하고 값을 저장할땐 int로 형변환하였음*/
    public void setLevelExp(Level level){
        double totalExp = level.getTotalExp();
        double requiredExp  = level.getRequiredExp();
        double exp = (totalExp / requiredExp) * 100; //현재 레벨의 경험치% 계산 어떻게할지 생각해야함
        level.setLevelExp((int) exp);
    }

    public Level findLevel(long memberId) {
        return levelRepository.findByMemberMemberId(memberId); //해당 회원에 대한 레벨정보 가져오기
    }
}
