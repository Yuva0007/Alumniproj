import React, { useState } from 'react';

export default function AlumniProfile({ alumniData }) {
  const [connected, setConnected] = useState(false);

  const handleConnect = () => {
    setConnected(true);
  };

  return (
    <div data-testid="alumni-profile">
      <h2 data-testid="alumni-name">{alumniData.name}</h2>
      <p data-testid="alumni-year">{alumniData.graduationYear}</p>
      <p data-testid="alumni-company">{alumniData.currentCompany}</p>
      <p data-testid="alumni-job">{alumniData.jobTitle}</p>
      <ul>
        {alumniData.skills.map((skill, index) => (
          <li key={index} data-testid="alumni-skill">{skill}</li>
        ))}
      </ul>
      {alumniData.availableForMentorship && (
        <span data-testid="mentorship-badge">Available for mentorship</span>
      )}
      <button
        data-testid="connect-btn"
        onClick={handleConnect}
        disabled={!alumniData.availableForMentorship}
      >
        Connect
      </button>
      {connected && (
        <p data-testid="connection-msg">
          Connection request sent to {alumniData.name}
        </p>
      )}
    </div>
  );
}
