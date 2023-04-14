import React from 'react';
import { Alert } from 'react-bootstrap';

const CustomAlert = ({ variant, message }) => {
  return (
    <Alert variant={variant}>
      {message}
    </Alert>
  );
};

export default CustomAlert;