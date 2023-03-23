package shop.mtcoding.jobara.company;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.jobara.common.ex.CustomApiException;
import shop.mtcoding.jobara.common.util.Hash;
import shop.mtcoding.jobara.common.util.MyBase64Decoder;
import shop.mtcoding.jobara.common.util.Verify;
import shop.mtcoding.jobara.company.dto.CompanyReq.CompanyJoinReqDto;
import shop.mtcoding.jobara.company.dto.CompanyReq.CompanyUpdateReqDto;
import shop.mtcoding.jobara.company.dto.CompanyResp.CompanyUpdateRespDto;
import shop.mtcoding.jobara.company.model.Company;
import shop.mtcoding.jobara.company.model.CompanyRepository;
import shop.mtcoding.jobara.user.model.User;
import shop.mtcoding.jobara.user.model.UserRepository;
import shop.mtcoding.jobara.user.vo.UserVo;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public CompanyUpdateRespDto getCompanyUpdateRespDto(Integer principalId) {
        User user = userRepository.findById(principalId);
        Company company = companyRepository.findByUserId(principalId);
        // CompanyUpdateRespDto companyUpdateRespDto = new
        // CompanyUpdateRespDto(user.getPassword(), user.getEmail(),
        // user.getAddress(),
        // user.getDetailAddress(), user.getTel(), company.getCompanyName(),
        // company.getCompanyScale(),
        // company.getCompanyField());
        CompanyUpdateRespDto companyUpdateRespDto = new CompanyUpdateRespDto(user, company);
        return companyUpdateRespDto;
    }

    @Transactional
    public void insertCompany(CompanyJoinReqDto companyJoinReqDto) {
        Verify.isNotEqualApi(
                userRepository.findByUsername(companyJoinReqDto.getUsername()), null, "이미 존재하는 유저네임 입니다.",
                HttpStatus.BAD_REQUEST);
        String salt = Hash.makeSalt();
        String hashPassword = Hash.encode(companyJoinReqDto.getPassword() + salt);
        User user = new User(companyJoinReqDto, hashPassword, salt);
        try {
            userRepository.insertForCompany(user);
            Company company = new Company(user.getId(), companyJoinReqDto.getCompanyName(),
                    companyJoinReqDto.getCompanyNumb());
            companyRepository.insert(company);
        } catch (Exception e) {
            throw new CustomApiException("서버 오류: 회원 가입 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public UserVo updateCompany(CompanyUpdateReqDto companyUpdateReqDto, Integer principalId) {
        String profilePath;
        try {
            profilePath = MyBase64Decoder.saveImage(companyUpdateReqDto.getProfile());
        } catch (Exception e) {
            throw new CustomApiException("서버 오류 : 파일 변환 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String salt = Hash.makeSalt();
        String hashPassword = Hash.encode(companyUpdateReqDto.getPassword() + salt);
        User user = new User(companyUpdateReqDto, principalId, profilePath, hashPassword, salt);
        Company company = new Company(companyUpdateReqDto, principalId);
        try {
            userRepository.updateById(user);
            companyRepository.updateByUserId(company);
        } catch (Exception e) {
            throw new CustomApiException("회원 수정 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        user = userRepository.findById(principalId);
        UserVo userVoPS = new UserVo(user.getId(), user.getUsername(), user.getProfile(), user.getRole());
        return userVoPS;
    }
}
