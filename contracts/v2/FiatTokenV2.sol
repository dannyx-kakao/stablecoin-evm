/**
 * Copyright 2023 Circle Internet Group, Inc. All rights reserved.
 *
 * SPDX-License-Identifier: Apache-2.0
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

pragma solidity 0.6.12;

import { IERC20 } from "@openzeppelin/contracts/token/ERC20/IERC20.sol";
import { EIP712Domain } from "./EIP712Domain.sol"; // solhint-disable-line no-unused-import
import { Blacklistable } from "../v1/Blacklistable.sol"; // solhint-disable-line no-unused-import
import { FiatTokenV1 } from "../v1/FiatTokenV1.sol";
import { EIP712 } from "../util/EIP712.sol";
import { EIP3009 } from "./EIP3009.sol";
import { EIP2612 } from "./EIP2612.sol";

// solhint-disable func-name-mixedcase

/**
 * @title FiatToken V2
 * @notice ERC20 Token backed by fiat reserves, version 2 (consolidated with V2.1 and V2.2 features)
 */
contract FiatTokenV2 is FiatTokenV1, EIP3009, EIP2612 {
    uint8 internal _initializedVersion;

    /**
     * @notice Initialize v2 (basic version for V1 -> V2 upgrades)
     * @param newName   New token name
     */
    function initializeV2(string calldata newName) external virtual {
        // solhint-disable-next-line reason-string
        require(initialized && _initializedVersion == 0);
        name = newName;
        _DEPRECATED_CACHED_DOMAIN_SEPARATOR = EIP712.makeDomainSeparator(
            newName,
            "2"
        );
        _initializedVersion = 1;
    }

    /**
     * @notice Initialize v2 (consolidated initialization for V2, V2.1, and V2.2)
     * @param newName               New token name
     * @param newSymbol             New token symbol
     * @param lostAndFound          The address to which the locked funds are sent
     * @param accountsToBlacklist   A list of accounts to migrate from the old blacklist
     */
    function initializeV2(
        string calldata newName,
        string calldata newSymbol,
        address lostAndFound,
        address[] calldata accountsToBlacklist
    ) external {
        // solhint-disable-next-line reason-string
        require(initialized && _initializedVersion == 0);

        // V2 initialization
        name = newName;
        _DEPRECATED_CACHED_DOMAIN_SEPARATOR = EIP712.makeDomainSeparator(
            newName,
            "2"
        );

        // V2.1 initialization: transfer locked funds and blacklist contract
        uint256 lockedAmount = _balanceOf(address(this));
        if (lockedAmount > 0) {
            _transfer(address(this), lostAndFound, lockedAmount);
        }
        _blacklist(address(this));

        // V2.2 initialization: update symbol and migrate blacklist
        symbol = newSymbol;

        // Add previously blacklisted accounts to the new blacklist data structure
        // and remove them from the old blacklist data structure.
        for (uint256 i = 0; i < accountsToBlacklist.length; i++) {
            require(
                _deprecatedBlacklisted[accountsToBlacklist[i]],
                "FiatTokenV2: Blacklisting previously unblacklisted account!"
            );
            _blacklist(accountsToBlacklist[i]);
            delete _deprecatedBlacklisted[accountsToBlacklist[i]];
        }
        _blacklist(address(this));
        delete _deprecatedBlacklisted[address(this)];

        _initializedVersion = 3;
    }

    /**
     * @notice Initialize v2.1 (for V2 -> V2.1 upgrades)
     * @param lostAndFound  The address to which the locked funds are sent
     */
    function initializeV2_1(address lostAndFound) external {
        // solhint-disable-next-line reason-string
        require(_initializedVersion == 1);

        uint256 lockedAmount = _balanceOf(address(this));
        if (lockedAmount > 0) {
            _transfer(address(this), lostAndFound, lockedAmount);
        }
        _blacklist(address(this));

        _initializedVersion = 2;
    }

    /**
     * @notice Initialize v2.2 (for V2.1 -> V2.2 upgrades)
     * @param accountsToBlacklist   A list of accounts to migrate from the old blacklist
     * @param newSymbol             New token symbol
     */
    function initializeV2_2(
        address[] calldata accountsToBlacklist,
        string calldata newSymbol
    ) external {
        // solhint-disable-next-line reason-string
        require(_initializedVersion == 2);

        // Update fiat token symbol
        symbol = newSymbol;

        // Add previously blacklisted accounts to the new blacklist data structure
        // and remove them from the old blacklist data structure.
        for (uint256 i = 0; i < accountsToBlacklist.length; i++) {
            require(
                _deprecatedBlacklisted[accountsToBlacklist[i]],
                "FiatTokenV2: Blacklisting previously unblacklisted account!"
            );
            _blacklist(accountsToBlacklist[i]);
            delete _deprecatedBlacklisted[accountsToBlacklist[i]];
        }
        _blacklist(address(this));
        delete _deprecatedBlacklisted[address(this)];

        _initializedVersion = 3;
    }

    /**
     * @notice Version string for the EIP712 domain separator
     * @return Version string
     */
    function version() external virtual pure returns (string memory) {
        return "2";
    }

    /**
     * @dev Internal function to increase the allowance by a given increment
     * @param owner     Token owner's address
     * @param spender   Spender's address
     * @param increment Amount of increase
     */
    function _increaseAllowance(
        address owner,
        address spender,
        uint256 increment
    ) internal override {
        _approve(owner, spender, allowed[owner][spender].add(increment));
    }

    /**
     * @dev Internal function to decrease the allowance by a given decrement
     * @param owner     Token owner's address
     * @param spender   Spender's address
     * @param decrement Amount of decrease
     */
    function _decreaseAllowance(
        address owner,
        address spender,
        uint256 decrement
    ) internal override {
        _approve(
            owner,
            spender,
            allowed[owner][spender].sub(
                decrement,
                "ERC20: decreased allowance below zero"
            )
        );
    }

    /**
     * @dev Internal function to get the current chain id.
     * @return The current chain id.
     */
    function _chainId() internal virtual view returns (uint256) {
        uint256 chainId;
        assembly {
            chainId := chainid()
        }
        return chainId;
    }

    /**
     * @inheritdoc EIP712Domain
     */
    function _domainSeparator()
        internal
        virtual
        override
        view
        returns (bytes32)
    {
        return EIP712.makeDomainSeparator(name, "2", _chainId());
    }

    /**
     * @notice Update allowance with a signed permit
     * @dev EOA wallet signatures should be packed in the order of r, s, v.
     * @param owner       Token owner's address (Authorizer)
     * @param spender     Spender's address
     * @param value       Amount of allowance
     * @param deadline    The time at which the signature expires (unix time), or max uint256 value to signal no expiration
     * @param signature   Signature bytes signed by an EOA wallet or a contract wallet
     */
    function permit(
        address owner,
        address spender,
        uint256 value,
        uint256 deadline,
        bytes memory signature
    ) external virtual whenNotPaused {
        _permit(owner, spender, value, deadline, signature);
    }

    /**
     * @notice Execute a transfer with a signed authorization
     * @dev EOA wallet signatures should be packed in the order of r, s, v.
     * @param from          Payer's address (Authorizer)
     * @param to            Payee's address
     * @param value         Amount to be transferred
     * @param validAfter    The time after which this is valid (unix time)
     * @param validBefore   The time before which this is valid (unix time)
     * @param nonce         Unique nonce
     * @param signature     Signature bytes signed by an EOA wallet or a contract wallet
     */
    function transferWithAuthorization(
        address from,
        address to,
        uint256 value,
        uint256 validAfter,
        uint256 validBefore,
        bytes32 nonce,
        bytes memory signature
    ) external virtual whenNotPaused notBlacklisted(from) notBlacklisted(to) {
        _transferWithAuthorization(
            from,
            to,
            value,
            validAfter,
            validBefore,
            nonce,
            signature
        );
    }

    /**
     * @notice Receive a transfer with a signed authorization from the payer
     * @dev This has an additional check to ensure that the payee's address
     * matches the caller of this function to prevent front-running attacks.
     * EOA wallet signatures should be packed in the order of r, s, v.
     * @param from          Payer's address (Authorizer)
     * @param to            Payee's address
     * @param value         Amount to be transferred
     * @param validAfter    The time after which this is valid (unix time)
     * @param validBefore   The time before which this is valid (unix time)
     * @param nonce         Unique nonce
     * @param signature     Signature bytes signed by an EOA wallet or a contract wallet
     */
    function receiveWithAuthorization(
        address from,
        address to,
        uint256 value,
        uint256 validAfter,
        uint256 validBefore,
        bytes32 nonce,
        bytes memory signature
    ) external virtual whenNotPaused notBlacklisted(from) notBlacklisted(to) {
        _receiveWithAuthorization(
            from,
            to,
            value,
            validAfter,
            validBefore,
            nonce,
            signature
        );
    }

    /**
     * @notice Attempt to cancel an authorization
     * @dev Works only if the authorization is not yet used.
     * EOA wallet signatures should be packed in the order of r, s, v.
     * @param authorizer    Authorizer's address
     * @param nonce         Nonce of the authorization
     * @param signature     Signature bytes signed by an EOA wallet or a contract wallet
     */
    function cancelAuthorization(
        address authorizer,
        bytes32 nonce,
        bytes memory signature
    ) external virtual whenNotPaused {
        _cancelAuthorization(authorizer, nonce, signature);
    }

    /**
     * @dev Helper method that sets the blacklist state of an account on balanceAndBlacklistStates.
     * If _shouldBlacklist is true, we apply a (1 << 255) bitmask with an OR operation on the
     * account's balanceAndBlacklistState. This flips the high bit for the account to 1,
     * indicating that the account is blacklisted.
     *
     * If _shouldBlacklist if false, we reset the account's balanceAndBlacklistStates to their
     * balances. This clears the high bit for the account, indicating that the account is unblacklisted.
     * @param _account         The address of the account.
     * @param _shouldBlacklist True if the account should be blacklisted, false if the account should be unblacklisted.
     */
    function _setBlacklistState(address _account, bool _shouldBlacklist)
        internal
        virtual
        override
    {
        balanceAndBlacklistStates[_account] = _shouldBlacklist
            ? balanceAndBlacklistStates[_account] | (1 << 255)
            : _balanceOf(_account);
    }

    /**
     * @dev Helper method that sets the balance of an account on balanceAndBlacklistStates.
     * Since balances are stored in the last 255 bits of the balanceAndBlacklistStates value,
     * we need to ensure that the updated balance does not exceed (2^255 - 1).
     * Since blacklisted accounts' balances cannot be updated, the method will also
     * revert if the account is blacklisted
     * @param _account The address of the account.
     * @param _balance The new fiat token balance of the account (max: (2^255 - 1)).
     */
    function _setBalance(address _account, uint256 _balance)
        internal
        virtual
        override
    {
        require(
            _balance <= ((1 << 255) - 1),
            "FiatTokenV2: Balance exceeds (2^255 - 1)"
        );
        require(
            !_isBlacklisted(_account),
            "FiatTokenV2: Account is blacklisted"
        );

        balanceAndBlacklistStates[_account] = _balance;
    }

    /**
     * @inheritdoc Blacklistable
     */
    function _isBlacklisted(address _account)
        internal
        virtual
        override
        view
        returns (bool)
    {
        return balanceAndBlacklistStates[_account] >> 255 == 1;
    }

    /**
     * @dev Helper method to obtain the balance of an account. Since balances
     * are stored in the last 255 bits of the balanceAndBlacklistStates value,
     * we apply a ((1 << 255) - 1) bit bitmask with an AND operation on the
     * balanceAndBlacklistState to obtain the balance.
     * @param _account  The address of the account.
     * @return          The fiat token balance of the account.
     */
    function _balanceOf(address _account)
        internal
        virtual
        override
        view
        returns (uint256)
    {
        return balanceAndBlacklistStates[_account] & ((1 << 255) - 1);
    }

    /**
     * @inheritdoc FiatTokenV1
     */
    function approve(address spender, uint256 value)
        external
        virtual
        override(FiatTokenV1, IERC20)
        whenNotPaused
        returns (bool)
    {
        _approve(msg.sender, spender, value);
        return true;
    }

    /**
     * @notice Execute a transfer with a signed authorization (v, r, s version)
     * @param from          Payer's address (Authorizer)
     * @param to            Payee's address
     * @param value         Amount to be transferred
     * @param validAfter    The time after which this is valid (unix time)
     * @param validBefore   The time before which this is valid (unix time)
     * @param nonce         Unique nonce
     * @param v             v of the signature
     * @param r             r of the signature
     * @param s             s of the signature
     */
    function transferWithAuthorization(
        address from,
        address to,
        uint256 value,
        uint256 validAfter,
        uint256 validBefore,
        bytes32 nonce,
        uint8 v,
        bytes32 r,
        bytes32 s
    ) external whenNotPaused notBlacklisted(from) notBlacklisted(to) {
        _transferWithAuthorization(
            from,
            to,
            value,
            validAfter,
            validBefore,
            nonce,
            v,
            r,
            s
        );
    }

    /**
     * @notice Receive a transfer with a signed authorization from the payer (v, r, s version)
     * @dev This has an additional check to ensure that the payee's address
     * matches the caller of this function to prevent front-running attacks.
     * @param from          Payer's address (Authorizer)
     * @param to            Payee's address
     * @param value         Amount to be transferred
     * @param validAfter    The time after which this is valid (unix time)
     * @param validBefore   The time before which this is valid (unix time)
     * @param nonce         Unique nonce
     * @param v             v of the signature
     * @param r             r of the signature
     * @param s             s of the signature
     */
    function receiveWithAuthorization(
        address from,
        address to,
        uint256 value,
        uint256 validAfter,
        uint256 validBefore,
        bytes32 nonce,
        uint8 v,
        bytes32 r,
        bytes32 s
    ) external whenNotPaused notBlacklisted(from) notBlacklisted(to) {
        _receiveWithAuthorization(
            from,
            to,
            value,
            validAfter,
            validBefore,
            nonce,
            v,
            r,
            s
        );
    }

    /**
     * @notice Attempt to cancel an authorization (v, r, s version)
     * @dev Works only if the authorization is not yet used.
     * @param authorizer    Authorizer's address
     * @param nonce         Nonce of the authorization
     * @param v             v of the signature
     * @param r             r of the signature
     * @param s             s of the signature
     */
    function cancelAuthorization(
        address authorizer,
        bytes32 nonce,
        uint8 v,
        bytes32 r,
        bytes32 s
    ) external whenNotPaused {
        _cancelAuthorization(authorizer, nonce, v, r, s);
    }

    /**
     * @notice Update allowance with a signed permit (v, r, s version)
     * @param owner       Token owner's address (Authorizer)
     * @param spender     Spender's address
     * @param value       Amount of allowance
     * @param deadline    The time at which the signature expires (unix time), or max uint256 value to signal no expiration
     * @param v           v of the signature
     * @param r           r of the signature
     * @param s           s of the signature
     */
    function permit(
        address owner,
        address spender,
        uint256 value,
        uint256 deadline,
        uint8 v,
        bytes32 r,
        bytes32 s
    ) external virtual whenNotPaused {
        _permit(owner, spender, value, deadline, v, r, s);
    }

    /**
     * @notice Increase the allowance by a given increment
     * @param spender   Spender's address
     * @param increment Amount of increase in allowance
     * @return True if successful
     */
    function increaseAllowance(address spender, uint256 increment)
        external
        virtual
        whenNotPaused
        returns (bool)
    {
        _increaseAllowance(msg.sender, spender, increment);
        return true;
    }

    /**
     * @notice Decrease the allowance by a given decrement
     * @param spender   Spender's address
     * @param decrement Amount of decrease in allowance
     * @return True if successful
     */
    function decreaseAllowance(address spender, uint256 decrement)
        external
        virtual
        whenNotPaused
        returns (bool)
    {
        _decreaseAllowance(msg.sender, spender, decrement);
        return true;
    }
}
