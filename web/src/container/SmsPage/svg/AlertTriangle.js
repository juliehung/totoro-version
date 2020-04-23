import React from 'react';

function AlertTriangle() {
  return (
    <svg xmlns="http://www.w3.org/2000/svg" width="17" height="16" viewBox="0 0 17 16">
      <path 
        fill="#FE9F43" 
        fill-rule="evenodd" 
        d="M9.167 8.667c0 .368-.299.666-.667.666-.368 0-.667-.298-.667-.666V6c0-.368.299-.667.667-.667.368 0 .667.299.667.667v2.667zM8.5 11.333c-.368 0-.667-.298-.667-.666 0-.368.299-.667.667-.667.368 0 .667.299.667.667 0 .368-.299.666-.667.666zm7.04-.464l-5.114-8.48c-.4-.661-1.12-1.056-1.926-1.056-.807 0-1.527.395-1.926 1.056l-5.115 8.48c-.378.628-.39 1.384-.03 2.022C1.815 13.575 2.565 14 3.385 14h10.23c.82 0 1.57-.425 1.956-1.11.36-.637.348-1.393-.03-2.021z"/>
    </svg>
  );
}

export default AlertTriangle;