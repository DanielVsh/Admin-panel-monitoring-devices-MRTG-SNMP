import { useState } from "react";
import modal from '../header/Modal.module.css'
import { useCallback } from "react";
import { useEffect } from "react";
import { useRef } from "react";
import { logout } from "../../auth/logout";
import jwt_decode from 'jwt-decode';
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";

function MenuModal() {
  const [showModal, setShowModal] = useState(false);

  const navigate = useNavigate()
  const dispatch = useDispatch()

  const modalWindowRef = useRef();
  const modalWindowRef2 = useRef();
  const handleClick = useCallback(
    (e) => {
      if (showModal
        && !modalWindowRef?.current?.contains(e?.target)
        && !modalWindowRef2?.current?.contains(e?.target)) {
        setShowModal(false);
      }
    },
    [showModal]
  );

  useEffect(() => {
    document.addEventListener("mousedown", handleClick);
    return () => {
      document.removeEventListener("mousedown", handleClick);
    };
  }, [handleClick]);

  const handleLogout = () => {
    logout(navigate, dispatch);
  };

  const handleSettings = () => {
    // реализуйте логику открытия страницы настроек
  };

  return (
    <>
      <i ref={modalWindowRef2} onClick={() => setShowModal(true)} className="bi bi-list"></i>
      {showModal && (
        <div className={modal.modal} ref={modalWindowRef}>
          <div>
            <p style={{ display: "inline-block", marginRight: "3px" }}>user:</p>
            <p style={{ display: "inline-block", color: "green" }}>{jwt_decode(localStorage.getItem("access_token"))?.sub}</p>
          </div>

          <p onClick={handleSettings}>Settings</p>
          <p onClick={handleLogout}>Logout</p>
        </div>
      )}
    </>
  );
}

export default MenuModal;
