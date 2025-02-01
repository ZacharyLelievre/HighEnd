import React from "react";
import { motion } from "framer-motion";
// Import the social icons you need:
import { FaInstagram, FaTiktok, FaFacebookF } from "react-icons/fa";
import "./Footer.css";

export const Footer: React.FC = () => {
    return (
        <motion.footer
            className="footer"
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.5 }}
        >
            <div className="footer-content">
                <div className="footer-details">
                    <p>© {new Date().getFullYear()} HighEndDetailing. All rights reserved.</p>
                    <p>Contact: info@highenddetailing.com</p>
                </div>
                <div className="social-links">
                    <motion.a
                        href="https://www.tiktok.com/@highend.detailing"
                        target="_blank"
                        rel="noopener noreferrer"
                        whileHover={{ scale: 1.1 }}
                    >
                        <FaTiktok />
                    </motion.a>
                    <motion.a
                        href="https://www.facebook.com/people/High-End-Detailing/100091911011369/"
                        target="_blank"
                        rel="noopener noreferrer"
                        whileHover={{ scale: 1.1 }}
                    >
                        <FaFacebookF />
                    </motion.a>
                    <motion.a
                        href="https://www.instagram.com/highend_detailing_/"
                        target="_blank"
                        rel="noopener noreferrer"
                        whileHover={{ scale: 1.1 }}
                    >
                        <FaInstagram />
                    </motion.a>
                </div>
            </div>
        </motion.footer>
    );
};