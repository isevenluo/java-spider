package org.pekxxoo.spider.dao;

import org.pekxxoo.spider.entity.Page;
import org.springframework.data.repository.Repository;

/**
 *  Repository interface to access {@link Page}s.
 * Created by chong on 2017/8/8.
 */
public interface PageRepository extends Repository<Page,Long>{

    /**
     * Returns the page with the given identifier.
     *
     * @param id
     * @return
     */
    Page findOne(Long id);


    /**
     * Saves the given {@link Page}. #
     *
     * @param page
     * @return
     */
    Page save(Page page);

    /**
     * Returns the {@link Page} with the given {@link String}.
     *
     * @param tvId
     * @return
     */
    Page findByTvId(String tvId);
}
