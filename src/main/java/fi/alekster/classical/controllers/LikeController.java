package fi.alekster.classical.controllers;

import fi.alekster.classical.controllers.utils.UserUtils;
import fi.alekster.classical.dao.ExLikeDao;
import fi.alekster.classical.db.tables.pojos.Like;
import fi.alekster.classical.representations.requests.ToggleLikeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by aleksandr on 13.12.2017.
 */
@RestController
@RequestMapping("/like")
public class LikeController {

    private final ExLikeDao likeDao;
    private final UserUtils userUtils;

    @Autowired
    public LikeController(ExLikeDao likeDao, UserUtils userUtils) {
        this.likeDao = likeDao;
        this.userUtils = userUtils;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> toggleLike (
            HttpServletRequest httpServletRequest,
            @RequestBody ToggleLikeRequest request
    ) {
        String userEmail = userUtils.getAuthenticatedCredential(httpServletRequest).getEmail();

        List<Like> existingLikes = likeDao.fetchByEmailAndPerformanceId(
                userEmail,
                request.getPerformanceId()
        );

        if (existingLikes.isEmpty()) {
            likeDao.insert(new Like(likeDao.count() + 1, userEmail, request.getPerformanceId()));
        } else {
            existingLikes.forEach(likeDao::delete);
        }

        return ResponseEntity.ok().build();
    }
}
