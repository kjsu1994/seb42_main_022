package community.board.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import community.board.entity.Board;
import community.board.entity.UploadFile;
import community.board.repository.UploadFileRepository;
import community.member.entity.Member;
import community.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class S3Service {

    private String S3Bucket = "kjs-project-upload"; // Bucket 이름
    private final AmazonS3Client amazonS3Client;
    private final UploadFileRepository uploadFileRepository;
    private final MemberService memberService;

    /*게시판 다중파일 업로드*/
    public List<UploadFile> uploadFiles(MultipartFile[] multipartFileList, Board board) throws Exception {
        if (multipartFileList == null) return uploadFileRepository.findByBoardBoardId(board.getBoardId());
            //파일을 선택적으로 업로드 할 수 있도록
        else {
            List<String> imagePathList = new ArrayList<>();

            for (MultipartFile multipartFile : multipartFileList) {
                String fileName = multipartFile.getOriginalFilename(); // 파일 이름
                long size = multipartFile.getSize(); // 파일 크기

                ObjectMetadata objectMetaData = new ObjectMetadata();
                objectMetaData.setContentType(multipartFile.getContentType());
                objectMetaData.setContentLength(size);

                // S3에 업로드
                amazonS3Client.putObject(
                        new PutObjectRequest(S3Bucket, fileName, multipartFile.getInputStream(), objectMetaData)
                                .withCannedAcl(CannedAccessControlList.PublicRead)
                );

                String imagePath = amazonS3Client.getUrl(S3Bucket, fileName).toString(); // 접근가능한 URL 가져오기
                imagePathList.add(imagePath); //String Type URL주소

                //엔티티에 저장하는 로직
                UploadFile uploadFile = new UploadFile();
                uploadFile.setBoard(board); //UploadFile 엔티티에 게시글Id 매핑
                uploadFile.setFileName(fileName);
                uploadFile.setImagePath(imagePath);
                uploadFileRepository.save(uploadFile);

                //대표이미지 저장  / 마지막 사진기준으로 저장
                board.setDelegateImagePath(uploadFile.getImagePath());
            }
            List<UploadFile> filelist = uploadFileRepository.findByBoardBoardId(board.getBoardId());
            return filelist;
        }
    }

    /*유저 프로필*/
    public Member userProfile(MultipartFile[] multipartFileList, long memberId) throws Exception {
        List<String> imagePathList = new ArrayList<>();
        Member member = memberService.findMember(memberId); // 프로필이 적용될 회원

        for (MultipartFile multipartFile : multipartFileList) {
            String fileName = multipartFile.getOriginalFilename(); // 파일 이름
            long size = multipartFile.getSize(); // 파일 크기

            ObjectMetadata objectMetaData = new ObjectMetadata();
            objectMetaData.setContentType(multipartFile.getContentType());
            objectMetaData.setContentLength(size);

            // S3에 업로드
            amazonS3Client.putObject(
                    new PutObjectRequest(S3Bucket, fileName, multipartFile.getInputStream(), objectMetaData)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );

            String imagePath = amazonS3Client.getUrl(S3Bucket, fileName).toString(); // 접근가능한 URL 가져오기
            imagePathList.add(imagePath); //String Type URL주소

            member.setProfileUrl(imagePath);
        }
        return member;
    }
}
