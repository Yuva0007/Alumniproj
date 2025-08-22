import React, { useState } from 'react';
import AlumniProfile from './AlumniProfile';

export default function AlumniList({ alumniList }) {
  const [page, setPage] = useState(1);
  const pageSize = 5;
  const totalPages = Math.ceil(alumniList.length / pageSize);

  if (!alumniList || alumniList.length === 0) {
    return (
      <div data-testid="alumni-empty">
        No alumni found matching your search criteria
      </div>
    );
  }

  const startIndex = (page - 1) * pageSize;
  const currentPageData = alumniList.slice(startIndex, startIndex + pageSize);

  return (
    <div>
      {currentPageData.map((alumni) => (
        <AlumniProfile key={alumni.id} alumniData={alumni} />
      ))}
      {totalPages > 1 && (
        <div>
          <button
            data-testid="prev-page-btn"
            onClick={() => setPage((p) => Math.max(p - 1, 1))}
            disabled={page === 1}
          >
            Prev
          </button>
          {/* NOTE: non-breaking space after "of" to match test */}
          <span data-testid="pagination-info">
            Page {page} of&nbsp;{totalPages}
          </span>
          <button
            data-testid="next-page-btn"
            onClick={() => setPage((p) => Math.min(p + 1, totalPages))}
            disabled={page === totalPages}
          >
            Next
          </button>
        </div>
      )}
    </div>
  );
}
