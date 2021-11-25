package io.dentall.totoro.service;

import io.dentall.totoro.domain.Hashtag;
import io.dentall.totoro.repository.HashtagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static io.dentall.totoro.domain.enumeration.HashTagType.PatientDocument;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toSet;

@Service
@Transactional
public class HashtagService {

    private final HashtagRepository hashtagRepository;


    public HashtagService(HashtagRepository hashtagRepository) {
        this.hashtagRepository = hashtagRepository;
    }

    public void processPatientDocumentTag(List<String> hashtagsOrigin, List<String> hashtagsModified) {
        // 將新增的tag找出後來，新增進資料庫裡
        Set<String> newHashtags = hashtagsModified.stream()
            .filter(hashtagLiteral -> !hashtagsOrigin.contains(hashtagLiteral))
            .filter(hashtagLiteral -> !hashtagRepository.existsPatientDocumentTagByTagName(hashtagLiteral))
            .peek(hashtagLiteral -> {
                Hashtag hashtag = new Hashtag();
                hashtag.setTagName(hashtagLiteral);
                hashtag.setTagType(PatientDocument);
                hashtag.setReferenceCount(1);
                hashtagRepository.save(hashtag);
            })
            .collect(toSet());

        // 將新增使用的tag，使用次數加1
        hashtagsModified.stream()
            .filter(hashtagLiteral -> !newHashtags.contains(hashtagLiteral))
            .filter(hashtagLiteral -> !hashtagsOrigin.contains(hashtagLiteral))
            .map(hashtagRepository::findPatientDocumentTagByTagName)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .peek(hashtag -> hashtag.setReferenceCount(hashtag.getReferenceCount() + 1))
            .map(Hashtag::getTagName)
            .collect(toSet());

        // 將移除使用的tag，使用次數減1
        hashtagsOrigin.stream()
            .filter(hashtagLiteral -> !hashtagsModified.contains(hashtagLiteral))
            .map(hashtagRepository::findPatientDocumentTagByTagName)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .peek(hashtag -> hashtag.setReferenceCount(hashtag.getReferenceCount() == 0 ? 0 : hashtag.getReferenceCount() - 1))
            .map(Hashtag::getTagName)
            .collect(toSet());
    }

    public void reducePatientDocumentTagReference(List<String> hashtagList) {
        if (isNull(hashtagList)) {
            return;
        }

        hashtagList.stream()
            .map(hashtagRepository::findPatientDocumentTagByTagName)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .peek(hashtag -> hashtag.setReferenceCount(hashtag.getReferenceCount() == 0 ? 0 : hashtag.getReferenceCount() - 1))
            .collect(toSet());
    }

}
