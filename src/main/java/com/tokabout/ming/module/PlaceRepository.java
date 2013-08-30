package com.tokabout.ming.module;

import com.tokabout.ming.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 13. 8. 30.
 */
public interface PlaceRepository extends JpaRepository<Place, Long> {
}
